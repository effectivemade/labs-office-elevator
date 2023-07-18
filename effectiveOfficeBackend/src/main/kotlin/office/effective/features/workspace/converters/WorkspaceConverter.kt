package office.effective.features.workspace.converters

import office.effective.features.workspace.repository.WorkspaceEntity
import office.effective.features.workspace.repository.WorkspaceTagEntity
import office.effective.model.Workspace
import java.lang.IllegalArgumentException
import java.util.UUID

class WorkspaceConverter {
    /**
     * Converts WorkspaceEntity to Workspace
     */
    fun entityToModel(entity: WorkspaceEntity): Workspace {
        return Workspace(entity.id, entity.name, entity.tag.name)
    }
    /**
     * Converts Workspace and WorkspaceTagEntity to WorkspaceEntity with random UUID
     *
     * Throws IllegalArgumentException if model.tag is not equal to tagEntity.name
     */
    fun modelToEntity(model: Workspace, tagEntity: WorkspaceTagEntity): WorkspaceEntity {
        if(tagEntity.name != model.tag) {
            throw IllegalArgumentException("model.tag=${model.tag} is not equal to tagEntity.name=${tagEntity.name}")
        }
        return WorkspaceEntity {
            id = UUID.randomUUID()
            name = model.name
            tag = tagEntity
        }
    }
}