package office.effective.features.workspace.repository

import office.effective.common.exception.WorkspaceTagNotFoundException
import office.effective.features.workspace.converters.WorkspaceConverter
import office.effective.model.Workspace
import org.koin.java.KoinJavaComponent.inject
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.*
import java.util.UUID

class WorkspaceRepository {
    private val database by inject<Database>(clazz = Database::class.java)
    private val converter by inject<WorkspaceConverter>(clazz = WorkspaceConverter::class.java)

    /**
     * Retrieves a workspace model by its id
     */
    fun findById(workspaceId: UUID): Workspace? {
        val entity: WorkspaceEntity? = database.workspaces.find { it.id eq workspaceId }
        return entity?.let { converter.entityToModel(it) }
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
            return entityList.map { converter.entityToModel(it) }
        }
    }

    /**
     * Saves a given workspace. If given model will have id, it will be ignored. Use the returned model for further operations
     *
     * Throws WorkspaceTagNotFoundException if tag doesn't exist in the database
     */
    fun save(workspace: Workspace): Workspace {
        val tagEntity: WorkspaceTagEntity? = database.workspaceTags.find { it.name eq workspace.tag }
        if (tagEntity == null) {
            throw WorkspaceTagNotFoundException("Workspace tag ${workspace.tag} not found")
        } else {
            workspace.id = UUID.randomUUID()
            database.workspaces.add(converter.modelToEntity(workspace, tagEntity))
            return workspace;
        }
    }

    /**
     * Deletes the workspace with the given id
     *
     * If the workspace is not found in the database it is silently ignored
     */
    fun deleteById(workspaceId: UUID) {
        database.workspaces.removeIf { it.id eq workspaceId }
    }
}