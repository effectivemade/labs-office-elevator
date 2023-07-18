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

    fun findById(workspaceId: UUID): Workspace? {
        val entity: WorkspaceEntity? = database.workspaces.find { it.id eq workspaceId }
        return entity?.let { converter.entityToModel(it) }
    }

    fun findAllByTag(tag: String): List<Workspace> {
        val tagEntity: WorkspaceTagEntity? = database.workspaceTags.find { it.name eq tag }
        if (tagEntity == null) {
            throw WorkspaceTagNotFoundException("Workspace tag $tag not found")
        } else {
            val entityList = database.workspaces.filter { it.tagId eq tagEntity.id }.toList()
            return entityList.map { converter.entityToModel(it) }
        }
    }

    fun save(workspace: Workspace): Workspace {
        val tagEntity: WorkspaceTagEntity? = database.workspaceTags.find { it.name eq workspace.tag }
        if (tagEntity == null) {
            throw WorkspaceTagNotFoundException("Workspace tag ${workspace.tag} not found")
        } else {
            database.workspaces.add(converter.modelToEntity(workspace, tagEntity))
            return workspace;
        }
    }

    fun deleteById(workspaceId: UUID) {
        database.workspaces.removeIf { it.id eq workspaceId }
    }
}