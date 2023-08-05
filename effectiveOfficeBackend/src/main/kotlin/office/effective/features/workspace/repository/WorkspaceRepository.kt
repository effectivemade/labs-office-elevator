package office.effective.features.workspace.repository

import office.effective.common.exception.InstanceNotFoundException
import office.effective.features.workspace.converters.WorkspaceRepositoryConverter
import office.effective.model.Utility
import office.effective.model.Workspace
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.*
import org.ktorm.support.postgresql.insertOrUpdate
import java.util.UUID

class WorkspaceRepository(private val database: Database, private val converter: WorkspaceRepositoryConverter) {

    /**
     * Returns whether a workspace with the given id exists
     *
     * @author Daniil Zavyalov
     */
    fun workspaceExistsById(workspaceId: UUID): Boolean {
        return database.workspaces.count { it.id eq workspaceId } > 0
    }

    /**
     * Returns whether a utility with the given id exists
     *
     * @author Daniil Zavyalov
     */
    fun utilityExistsById(utilityId: UUID): Boolean {
        return database.utilities.count { it.id eq utilityId } > 0
    }

    /**
     * Returns all workspace utilities by workspace id
     *
     * Throws InstanceNotFoundException if workspace with given id doesn't exist in the database
     *
     * @author Daniil Zavyalov
     */
    fun findUtilitiesByWorkspaceId(workspaceId: UUID): List<Utility> {
        if (!workspaceExistsById(workspaceId)) {
            throw InstanceNotFoundException(WorkspaceEntity::class, "Workspace with id $workspaceId not found", workspaceId)
        }
        return findUtilityModels(workspaceId)
    }

    /**
     * Returns all workspace utilities by workspace id
     *
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
     * Retrieves a workspace model by its id
     *
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
     * Throws InstanceNotFoundException if tag doesn't exist in the database
     *
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
     * Adds utility to workspace by their id.
     * If the utility has already been added to the workspace, the count value will be overwritten
     *
     * Throws InstanceNotFoundException if workspace or utility with given id doesn't exist in the database
     *
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
     * @author Daniil Zavyalov
     */
    @Deprecated("API does not involve deleting workspace entities")
    fun deleteById(workspaceId: UUID) {
        database.workspaces.removeIf { it.id eq workspaceId }
    }
}