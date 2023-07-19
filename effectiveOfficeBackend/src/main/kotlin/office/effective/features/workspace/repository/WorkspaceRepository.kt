package office.effective.features.workspace.repository

import office.effective.common.exception.UtilityNotFoundException
import office.effective.common.exception.WorkspaceNotFoundException
import office.effective.common.exception.WorkspaceTagNotFoundException
import office.effective.features.workspace.converters.WorkspaceRepositoryConverter
import office.effective.model.Utility
import office.effective.model.Workspace
import org.koin.java.KoinJavaComponent.inject
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.*
import org.ktorm.support.postgresql.insertOrUpdate
import java.util.UUID

class WorkspaceRepository {
    private val database by inject<Database>(clazz = Database::class.java)
    private val converter by inject<WorkspaceRepositoryConverter>(
        clazz = WorkspaceRepositoryConverter::class.java
    )

    /**
     * Returns whether a workspace with the given id exists
     */
    fun workspaceExistsById(workspaceId: UUID): Boolean {
        return database.workspaces.count { it.id eq workspaceId } > 0
    }

    /**
     * Returns whether a utility with the given id exists
     */
    fun utilityExistsById(utilityId: UUID): Boolean {
        return database.utilities.count { it.id eq utilityId } > 0
    }

    /**
     * Returns all workspace utilities by workspace id
     *
     * Throws WorkspaceNotFoundException if workspace with given id doesn't exist in the database
     */
    private fun findUtilitiesByWorkspaceId(workspaceId: UUID): List<Utility> {
        if (!workspaceExistsById(workspaceId)) {
            throw WorkspaceNotFoundException("Workspace with id $workspaceId not found")
        }
        val modelList = database
            .from(WorkspaceUtilities)
            .innerJoin(right = Utilities, on = WorkspaceUtilities.utilityId eq Utilities.id)
            .select()
            .where { WorkspaceUtilities.workspaceId eq workspaceId }
            .map { row ->
                converter.utilityEntityToModel(
                    Utilities.createEntity(row), row[WorkspaceUtilities.count]?:0
                )
            }
        return modelList
    }

    /**
     * Retrieves a workspace model by its id
     */
    fun findById(workspaceId: UUID): Workspace? {
        val entity: WorkspaceEntity? = database.workspaces.find { it.id eq workspaceId }
        val utilities: List<Utility> = findUtilitiesByWorkspaceId(workspaceId)
        return entity?.let { converter.entityToModel(it, utilities) }
    }

    /**
     * Returns all workspaces with the given tag
     *
     * Throws WorkspaceTagNotFoundException if tag doesn't exist in the database
     */
    fun findAllByTag(tag: String): List<Workspace> {
        val tagEntity: WorkspaceTagEntity? = database.workspaceTags.find { it.name eq tag }
        if (tagEntity == null) {
            throw WorkspaceTagNotFoundException("Workspace tag $tag not found")
        } else {
            val entityList = database.workspaces.filter { it.tagId eq tagEntity.id }.toList()
            return entityList.map {
                val utilities: List<Utility> = findUtilitiesByWorkspaceId(it.id)
                converter.entityToModel(it, utilities)
            }
        }
    }

    /**
     * Adds utility to workspace by their id.
     * If the utility has already been added to the workspace, the count value will be overwritten
     *
     * Throws WorkspaceNotFoundException if workspace with given id doesn't exist in the database
     *
     * Throws UtilityNotFoundException if utility with given id doesn't exist in the database
     */
    fun addUtilityToWorkspace(utilityId: UUID, workspaceId: UUID, count: UInt) {
        if (!workspaceExistsById(workspaceId)) {
            throw WorkspaceNotFoundException("Workspace with id $workspaceId not found")
        }
        if (!utilityExistsById(utilityId)) {
            throw UtilityNotFoundException("Utility with id $utilityId not found")
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
     * Throws WorkspaceTagNotFoundException if tag doesn't exist in the database
     */
    @Deprecated("API does not involve saving workspace entities")
    fun save(workspace: Workspace): Workspace {
        val tagEntity: WorkspaceTagEntity? = database.workspaceTags.find { it.name eq workspace.tag }
        if (tagEntity == null) {
            throw WorkspaceTagNotFoundException("Workspace tag ${workspace.tag} not found")
        } else {
            val entity = converter.modelToEntity(workspace, tagEntity);
            database.workspaces.add(entity)
            for (utility in workspace.utilities) {
                addUtilityToWorkspace(utility.id, entity.id, utility.count.toUInt())
            }
            return workspace;
        }
    }

    /**
     * Deletes the workspace with the given id
     *
     * If the workspace is not found in the database it is silently ignored
     */
    @Deprecated("API does not involve deleting workspace entities")
    fun deleteById(workspaceId: UUID) {
        database.workspaces.removeIf { it.id eq workspaceId }
    }
}