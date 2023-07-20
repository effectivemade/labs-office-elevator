package office.effective.feature.auth.repository

import office.effective.common.exception.IntegrationNotFoundException
import office.effective.common.exception.UserIntegrationNotFoundException
import office.effective.common.exception.UserNotFoundException
import office.effective.common.exception.UserTagNotFoundException
import office.effective.feature.auth.converters.UserModelEntityConverter
import office.effective.model.IntegrationModel
import office.effective.model.UserModel
import office.effective.model.UserTagModel
import org.koin.core.context.GlobalContext
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.*
import java.util.*

class UserRepository {
    private val db: Database = GlobalContext.get().get()
    private val converter: UserModelEntityConverter = GlobalContext.get().get()


    fun existsById(userId: UUID): Boolean {
        return db.users.count { it.id eq userId } > 0
    }

    fun findById(userId: UUID): UserModel {
        val userEnt: UserEntity =
            db.users.find { it.id eq userId } ?: throw UserNotFoundException("DB sync error")
        val integrations = findSetOfIntegrationsByUser(userEnt.id!!)
        val tagEntity =
            db.users_tags.find { it.id eq userEnt.tag.id } ?: throw UserTagNotFoundException("DB sync error")

        val userModel = converter.EntityToModel(userEnt, null)
        userModel.integrations = integrations
        userModel.tag = tagEntity

        return userModel
    }

    fun findByTag(tagId: UUID): Set<UserModel> {

        val ents = db.users.filter { it.tagId eq tagId }.toSet()
        var models: MutableSet<UserModel> = mutableSetOf<UserModel>()
        ents.forEach {
            val user = converter.EntityToModel(it, null)
            user.integrations = findSetOfIntegrationsByUser(user.id!!)
            models.add(user)

        }
        return models
    }

    fun findByEmail(email: String): UserModel {
        val integrationUserEntity: UserIntegrationEntity = db.usersinegrations.find { it.valueStr eq email }
            ?: throw UserIntegrationNotFoundException("not such email");
        return findById(integrationUserEntity.userId.id)
    }

    fun findIntegrationById(id: UUID): IntegrationEntity {
        return db.integrations.find { it.id eq id }
            ?: throw IntegrationNotFoundException("There are no integration with such id")
    }

    fun findSetOfIntegrationsByUser(userId: UUID): MutableSet<IntegrationModel> {

        val usersIntegrationsSet: Set<UserIntegrationEntity> =
            db.usersinegrations.filter { it.userId eq userId }.toSet()
        var modelsSet: MutableSet<IntegrationModel> = mutableSetOf()

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

    fun findTagByName(tagName: String): UserTagModel {
        val tag = db.users_tags.find { it.name eq tagName } ?: throw UserTagNotFoundException("Wrong tag name")
        return UserTagModel(tag.id, tag.name)
    }


}
