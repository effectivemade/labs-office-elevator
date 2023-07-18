package office.effective.features.workspace.converters

import office.effective.features.workspace.repository.WorkspaceEntity
import office.effective.model.Workspace

class WorkspaceConverter {
    fun entityToModel(entity: WorkspaceEntity): Workspace {
        return Workspace(entity.id, entity.name, entity.tag.name)
    }
    /*fun ModelToEntity(model: Workspace): WorkspaceEntity {
        return WorkspaceEntity {
            id = model.id
            name = model.name
            tag = model.tagId
        }
    }*/
}