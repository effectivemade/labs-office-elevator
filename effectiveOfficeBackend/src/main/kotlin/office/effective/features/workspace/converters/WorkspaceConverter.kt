package office.effective.features.workspace.converters

import office.effective.features.workspace.repository.WorkspaceEntity
import office.effective.features.workspace.repository.WorkspaceTagEntity
import office.effective.model.Workspace
import java.lang.IllegalArgumentException

class WorkspaceConverter {
    fun entityToModel(entity: WorkspaceEntity): Workspace {
        return Workspace(entity.id, entity.name, entity.tag.name)
    }
    fun modelToEntity(model: Workspace, tagEntity: WorkspaceTagEntity): WorkspaceEntity {
        if(tagEntity.name != model.tag) {
            throw IllegalArgumentException("model.tag=${model.tag} is not equal to tagEntity.name=${tagEntity.name}")
        }
        return WorkspaceEntity {
            id = model.id
            name = model.name
            tag = tagEntity
        }
    }
}