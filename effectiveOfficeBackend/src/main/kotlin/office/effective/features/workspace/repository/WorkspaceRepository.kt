package office.effective.features.workspace.repository

import office.effective.common.exception.InstanceNotFoundException
import office.effective.features.booking.repository.BookingCalendarRepository
import office.effective.features.booking.repository.IBookingRepository
import office.effective.features.booking.repository.WorkspaceBooking
import office.effective.features.workspace.converters.WorkspaceRepositoryConverter
import office.effective.model.IntegrationModel
import office.effective.model.Utility
import office.effective.model.Workspace
import office.effective.model.WorkspaceZone
import org.koin.core.context.GlobalContext
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.*
import org.ktorm.support.postgresql.insertOrUpdate
import java.time.Instant
import java.util.UUID

/**
 * Class that executes database queries with workspaces
 *
 * All WorkspaceRepository methods return workspaces with workspace zone and all utilities
 */
class WorkspaceRepository(private val database: Database, private val converter: WorkspaceRepositoryConverter) {

    /**
     * Returns whether a workspace with the given id exists.
     *
     * TODO: existence check temporary always returns true
     * @param workspaceId id of requested workspace
     * @return true if [Workspace] with the given [workspaceId] exists in the database
     * @author Daniil Zavyalov
     */
    fun workspaceExistsById(workspaceId: UUID): Boolean {
        return true// database.workspaces.count { it.id eq workspaceId } > 0 TODO: FIX ME< ONII CHAN
    }

    /**
     * Returns whether a utility with the given id exists
     *
     * @param utilityId id of requested utility
     * @return true if [Utility] with the given [utilityId] exists in the database
     * @author Daniil Zavyalov
     */
    fun utilityExistsById(utilityId: UUID): Boolean {
        return database.utilities.count { it.id eq utilityId } > 0
    }

    /**
     * Returns all workspace utilities by workspace id.
     *
     * @param workspaceId
     * @return List of [Utility] for [Workspace] with the given id
     * @throws InstanceNotFoundException if workspace with given id doesn't exist in the database
     * @author Daniil Zavyalov
     */
    fun findUtilitiesByWorkspaceId(workspaceId: UUID): List<Utility> {
        if (!workspaceExistsById(workspaceId)) {
            throw InstanceNotFoundException(WorkspaceEntity::class, "Workspace with id $workspaceId not found", workspaceId)
        }
        return findUtilityModels(workspaceId)
    }

    /**
     * Returns all workspace utilities by workspace id without throwing an exception
     *
     * @param workspaceId
     * @return List of [Utility] for [Workspace] with the given id
     * @author Daniil Zavyalov
     */
    private fun findUtilityModels(workspaceId: UUID): List<Utility> {
        return database
            .from(WorkspaceUtilities)
            .innerJoin(right = Utilities, on = WorkspaceUtilities.utilityId eq Utilities.id)
            .select()
            .where { WorkspaceUtilities.workspaceId eq workspaceId }
            .map { row ->
                converter.utilityEntityToModel(
                    Utilities.createEntity(row), row[WorkspaceUtilities.count]?:0
                )
            }
    }

    /**
     * Finds utilities for multiple workspaces in a single query.
     *
     * To reduce the number of database queries, use this method when you need to get utilities for multiple workspaces.
     *
     * @return Returns a HashMap that maps user ids and lists with their integrations
     * @throws InstanceNotFoundException if user with the given id doesn't exist in the database
     *
     * @author Daniil Zavyalov
     * */
    fun findAllUtilitiesByWorkspaceIds(ids: Collection<UUID>): HashMap<UUID, MutableList<Utility>> {
        if (ids.isEmpty()) {
            return HashMap<UUID, MutableList<Utility>>()
        }
        for (id in ids) {
            if (!workspaceExistsById(id))
                throw InstanceNotFoundException(WorkspaceEntity::class, "Workspace with id $id not found")
        }
        val result = hashMapOf<UUID, MutableList<Utility>>()
        database
            .from(WorkspaceUtilities)
            .innerJoin(right = Utilities, on = WorkspaceUtilities.utilityId eq Utilities.id)
            .select()
            .where { WorkspaceUtilities.workspaceId inList ids }
            .forEach { row ->
                val workspaceId: UUID = row[WorkspaceUtilities.workspaceId] ?: return@forEach
                val utility = converter.utilityEntityToModel(
                    Utilities.createEntity(row), row[WorkspaceUtilities.count] ?: 0
                )
                val utilities: MutableList<Utility> = result.getOrPut(workspaceId) { mutableListOf() }
                utilities.add(utility)
            }
        return result
    }

    /**
     * Retrieves a workspace model by its id
     *
     * @param workspaceId id of requested workspace
     * @return [Workspace] with the given [workspaceId] or null if workspace with the given id doesn't exist
     * @author Daniil Zavyalov
     */
    fun findById(workspaceId: UUID): Workspace? {
        val entity: WorkspaceEntity = database.workspaces.find { it.id eq workspaceId } ?: return null
        val utilities: List<Utility> = findUtilityModels(workspaceId)
        return converter.entityToModel(entity, utilities)
    }

    /**
     * Returns all workspaces with the given tag
     *
     * @param tag tag name of requested workspaces
     * @return List of [Workspace] with the given [tag]
     * @throws InstanceNotFoundException if tag doesn't exist in the database
     * @author Daniil Zavyalov
     */
    fun findAllByTag(tag: String): List<Workspace> {
        val tagEntity: WorkspaceTagEntity = database.workspaceTags.find { it.name eq tag }
            ?: throw InstanceNotFoundException(WorkspaceTagEntity::class, "Workspace tag $tag not found")

        val entityList = database.workspaces.filter { it.tagId eq tagEntity.id }.toList()
        return entityList.map {
            val utilities: List<Utility> = findUtilityModels(it.id)
            converter.entityToModel(it, utilities)
        }
    }

    /**
     * Returns all workspaces with the given tag which are free during the given period
     *
     * @param tag tag name of requested workspaces
     * @param beginTimestamp period start time
     * @param endTimestamp period end time
     * @return List of [Workspace] with the given [tag]
     * @throws InstanceNotFoundException if tag doesn't exist in the database
     * @author Daniil Zavyalov
     */
    fun findAllFreeByPeriod(tag: String, beginTimestamp: Instant, endTimestamp: Instant): List<Workspace> {
        if (database.workspaceTags.count { it.name eq tag } == 0)
            throw InstanceNotFoundException(WorkspaceTagEntity::class, "Workspace tag $tag not found")

        val calendarRepository: IBookingRepository = GlobalContext.get().get() //TODO: REWRITE IT FROM SCRATCH
        val freeWorkspaces = mutableListOf<Workspace>()
        val bookings = calendarRepository.findAll()
        findAllByTag(tag).forEach {
            if (bookings.none { booking ->
                booking.beginBooking.isBefore(endTimestamp) && booking.endBooking.isAfter(beginTimestamp)
            }) {
                freeWorkspaces.add(it)
            }
        }
        return freeWorkspaces
    }

    /**
     * Returns all workspace zones
     *
     * @return List of all [WorkspaceZone]
     * @author Daniil Zavyalov
     */
    fun findAllZones(): List<WorkspaceZone> {
        return database.workspaceZones.map { converter.zoneEntityToModel(it) }
    }

    /**
     * Adds utility to workspace by their id.
     * If the utility has already been added to the workspace, the count value will be overwritten
     *
     * @param utilityId
     * @param workspaceId
     * @param count quantity of the given utility in the workspace
     * @throws InstanceNotFoundException if workspace or utility with given id doesn't exist in the database
     * @author Daniil Zavyalov
     */
    @Deprecated("API does not involve adding utility to workspaces")
    fun addUtilityToWorkspace(utilityId: UUID, workspaceId: UUID, count: UInt) {
        if (!workspaceExistsById(workspaceId)) {
            throw InstanceNotFoundException(WorkspaceEntity::class, "Workspace with id $workspaceId not found", workspaceId)
        }
        if (!utilityExistsById(utilityId)) {
            throw InstanceNotFoundException(UtilityEntity::class, "Utility with id $utilityId not found", utilityId)
        }
        database.insertOrUpdate(WorkspaceUtilities) {
            set(it.workspaceId, workspaceId)
            set(it.utilityId, utilityId)
            set(it.count, count.toInt())
            onConflict {
                set(it.count, count.toInt())
            }
        }
    }

    /**
     * Saves a given workspace. If given model will have id, it will be ignored. Use the returned model for further operations
     *
     * @param workspace [Workspace] to be saved
     * @return saved [Workspace]
     * @author Daniil Zavyalov
     */
    @Deprecated("API does not involve saving workspace entities")
    fun save(workspace: Workspace): Workspace {
        val entity = converter.modelToEntity(workspace);
        database.workspaces.add(entity)
        for (utility in workspace.utilities) {
            addUtilityToWorkspace(utility.id, entity.id, utility.count.toUInt())
        }
        return workspace;
    }

    /**
     * Deletes the workspace with the given id
     *
     * If the workspace is not found in the database it is silently ignored
     *
     * @param workspaceId
     * @author Daniil Zavyalov
     */
    @Deprecated("API does not involve deleting workspace entities")
    fun deleteById(workspaceId: UUID) {
        database.workspaces.removeIf { it.id eq workspaceId }
    }
}