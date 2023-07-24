package office.effective.features.workspace.converters

import office.effective.features.workspace.repository.UtilityEntity
import office.effective.features.workspace.repository.WorkspaceEntity
import office.effective.features.workspace.repository.WorkspaceTagEntity
import office.effective.model.Utility
import office.effective.model.Workspace
import java.lang.IllegalArgumentException
import java.util.UUID

class WorkspaceRepositoryConverter {
    /**
     * Converts WorkspaceEntity to Workspace
     *
     * @author Daniil Zavyalov
     */
    fun entityToModel(entity: WorkspaceEntity, utilities: List<Utility>): Workspace {
        return Workspace(entity.id, entity.name, entity.tag.name, utilities)
    }
    /**
     * Converts Workspace and WorkspaceTagEntity to WorkspaceEntity with random UUID
     *
     * Throws IllegalArgumentException if model.tag is not equal to tagEntity.name
     *
     * @author Daniil Zavyalov
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

    /**
     * Converts UtilityEntity to Utility with given count
     *
     * @author Daniil Zavyalov
     */
    fun utilityEntityToModel(entity: UtilityEntity, count: Int): Utility {
        return Utility(entity.id, entity.name, entity.iconUrl, count)
    }
}