package office.effective.features.user.repository

import office.effective.common.exception.*
import office.effective.features.user.converters.IntegrationModelEntityConverter
import office.effective.features.user.converters.UserModelEntityConverter
import office.effective.model.IntegrationModel
import office.effective.model.UserModel
import office.effective.model.UserTagModel
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.*
import java.util.*

class UserRepository(
    private val db: Database,
    private val converter: UserModelEntityConverter,
    private val integrationConverter: IntegrationModelEntityConverter
) {

    /**
     * Checks existence of user by id, using count
     * @return Boolean (true - exists)
     *
     * @author Danil Kiselev
     * */
    fun existsById(userId: UUID): Boolean {
        return true// db.users.count { it.id eq userId } > 0 TODO: FIX ME, SEMPAI
    }

    /**
     * @return UserModel if user exists
     * @throws InstanceNotFoundException if user not found
     *
     * @author Danil Kiselev
     * */
    fun findById(userId: UUID): UserModel? {
        val userEnt: UserEntity? = db.users.find { it.id eq userId }

        val integrations = findSetOfIntegrationsByUser(userId)

        return userEnt?.let { converter.entityToModel(userEnt, integrations) }
    }

    /**
     * Retrieves all users
     * @return Set<UserModel>
     *
     * @author Daniil Zavyalov
     * */
    fun findAll(): Set<UserModel> {
        val entities = db.users.toSet()
        if (entities.isEmpty()) return emptySet()

        val ids = mutableSetOf<UUID>()

        for (entity in entities) {
            ids.add(entity.id)
        }
        val integrations = findAllIntegrationsByUserIds(ids)

        val models = mutableSetOf<UserModel>()
        for (entity in entities) {
            models.add(converter.entityToModel(entity, integrations[entity.id]))
        }
        return models
    }

    /**
     * Used to find multiple users with one tag
     * @return Set<UserModel> - users with tag name like input
     *
     * @author Danil Kiselev
     * */
    fun findByTag(tagId: UUID): Set<UserModel> {

        val ents = db.users.filter { Users.tagId eq tagId }.toSet()
        val models: MutableSet<UserModel> = mutableSetOf<UserModel>()
        ents.forEach {
            val user = converter.entityToModel(it, null)
            user.integrations = findSetOfIntegrationsByUser(user.id!!)
            models.add(user)

        }
        return models
    }

    /**
     * Retrieves a user model by email
     *
     * @return UserModel
     * @throws InstanceNotFoundException if user with the given email doesn't exist in database
     * @author Daniil Zavyalov
     */
    fun findByEmail(email: String): UserModel? {
        val entity: UserEntity? = db.users.find { it.email eq email }
        //?: throw InstanceNotFoundException(UserEntity::class, "User with email $email not found")
//        if (entity == null) {
//            return UserModel(
//                fullName = "",
//                id = UUID.randomUUID(),
//                tag = null,
//                active = false,
//                role = "",
//                avatarURL = "",
//                integrations = null,
//                email = email
//            )
//        }
        var integrations: MutableSet<IntegrationModel>? = null

        if (entity != null) {
            integrations = findSetOfIntegrationsByUser(entity.id)
        }

        return entity?.let { converter.entityToModel(entity, integrations) }
    }

    /**
     * Returns Integration entity by id
     * @return IntegrationEntity
     * @throws InstanceNotFoundException(IntegrationEntity, "Integration with id ${id} not found")
     *
     * @author Danil Kiselev
     * */
    private fun findIntegrationById(id: UUID): IntegrationEntity {
        return db.integrations.find { it.id eq id } ?: throw InstanceNotFoundException(
            IntegrationEntity::class, "Integration with id ${id} not found"
        )
    }

    /**
     * Returns all integrations of user (by user id)
     * @return MutableSet<IntegrationModel>
     * @throws InstanceNotFoundException(UserEntity::class, "User $userId")
     *
     * @author Danil Kiselev
     * */
    fun findSetOfIntegrationsByUser(userId: UUID): MutableSet<IntegrationModel> {
        if (!existsById(userId)) {
            throw InstanceNotFoundException(UserEntity::class, "User $userId")
        }
        val usersIntegrationsSet: Set<UserIntegrationEntity> =
            db.usersinegrations.filter { it.userId eq userId }.toSet()
        val modelsSet: MutableSet<IntegrationModel> = mutableSetOf()

        usersIntegrationsSet.forEach {
            val integrationEntity = findIntegrationById(it.integrationId.id!!)
            modelsSet.add(
                IntegrationModel(
                    id = integrationEntity.id!!,
                    name = integrationEntity.name,
                    valueStr = it.valueStr,
                    iconUrl = integrationEntity.iconUrl
                )
            )
        }
        return modelsSet;
    }

    /**
     * Returns a HashMap that maps user ids and their integrations
     * @return HashMap<UUID, MutableSet<IntegrationModel>>
     * @throws InstanceNotFoundException if user with the given id doesn't exist in the database
     *
     * @author Daniil Zavyalov
     * */
    fun findAllIntegrationsByUserIds(ids: Collection<UUID>): HashMap<UUID, MutableSet<IntegrationModel>> {
        if (ids.isEmpty()) {
            return HashMap<UUID, MutableSet<IntegrationModel>>()
        }
        for (id in ids) {
            if (!existsById(id)) throw InstanceNotFoundException(UserEntity::class, "User with id $id not found")
        }
        val result = hashMapOf<UUID, MutableSet<IntegrationModel>>()
        db.from(UsersIntegrations)
            .innerJoin(right = Integrations, on = UsersIntegrations.integrationId eq Integrations.id).select()
            .where { UsersIntegrations.integrationId inList ids }.forEach { row ->
                val userId: UUID = row[UsersIntegrations.integrationId] ?: return@forEach
                val utility = integrationConverter.entityToModel(
                    Integrations.createEntity(row), row[UsersIntegrations.valueStr] ?: ""
                )
                val integrations: MutableSet<IntegrationModel> = result.getOrPut(userId) { mutableSetOf() }
                integrations.add(utility)
            }
        return result
    }

    /**
     * Returns TagModel by value
     * @return UserTagModel
     * @throws InstanceNotFoundException(UsersTagEntity::class, "Tag with name ${tagName} not found")
     *
     * @author Danil Kiselev
     * */
    fun findTagByName(tagName: String): UserTagModel {
        val tag = db.users_tags.find { it.name eq tagName } ?: throw InstanceNotFoundException(
            UsersTagEntity::class, "Tag with name ${tagName} not found"
        )
        return UserTagModel(tag.id, tag.name)
    }

    /**
     * Updates a given user. Use the returned model for further operations
     *
     * @throws MissingIdException if the user or integration id is null
     *
     * @throws InstanceNotFoundException if the given user or integration don't exist
     *
     * @author Danil Kiselev, Daniil Zavyalov
     */
    fun updateUser(model: UserModel): UserModel {
        val userId = model.id?.let {
            if (!existsById(it)) throw InstanceNotFoundException(UserEntity::class, "User with id $it not wound", it)
            it
        } ?: throw MissingIdException("User with name ${model.fullName} doesn't have an id")

        val ent = db.users.find { it.id eq userId }
        ent?.tag = model.tag!!
        ent?.fullName = model.fullName
        ent?.active = model.active
        ent?.avatarURL = model.avatarURL
        ent?.role = model.role
        ent?.email = model.email
        ent?.flushChanges()

        val integrations: Set<IntegrationModel>? = model.integrations
        if (!integrations.isNullOrEmpty()) {
            saveIntegrations(integrations, userId)
        }
        return model
    }

    /**
     * Adds many-to-many relationship between user and its integrations
     *
     * @throws MissingIdException if the integration id is null
     *
     * @throws InstanceNotFoundException if the given integration don't exist
     *
     * @author Daniil Zavyalov
     */
    private fun saveIntegrations(integrationModels: Set<IntegrationModel>, userId: UUID) {
        db.usersinegrations.removeIf { it.userId eq userId }
        for (integration in integrationModels) {
            val integrationId: UUID = integration.id
                ?: throw MissingIdException("Integration with name ${integration.name} doesn't have an id")

            db.integrations.find { it.id eq integrationId } ?: throw InstanceNotFoundException(
                IntegrationEntity::class, "User with id $integrationId not found", integrationId
            )
            db.insert(UsersIntegrations) {
                set(it.userId, userId)
                set(it.integrationId, integrationId)
                set(it.valueStr, integration.valueStr)
            }
        }
    }

    /**
     * Returns Tag entity or null, if user with such id doesn't exist
     * @return UsersTagEntity?
     *
     * @author Danil Kiselev
     * */
    fun findTagByUserOrNull(userId: UUID): UsersTagEntity? {
        if (!existsById(userId)) return null
        val ent = findById(userId)
        return ent?.tag
    }
}
