package office.effective.features.workspace.converters

import office.effective.common.exception.InstanceNotFoundException
import office.effective.features.workspace.repository.*
import office.effective.model.Utility
import office.effective.model.Workspace
import office.effective.model.WorkspaceZone
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.find
import java.lang.IllegalArgumentException
import java.util.UUID

class WorkspaceRepositoryConverter(private val database: Database) {

    /**
     * Converts WorkspaceEntity to Workspace
     *
     * @author Daniil Zavyalov
     */
    fun entityToModel(entity: WorkspaceEntity, utilities: List<Utility>): Workspace {
        return Workspace(
            entity.id,
            entity.name,
            entity.tag.name,
            utilities,
            entity.zone?.let { (zoneEntityToModel(it)) }
        )
    }

    /**
     * Converts UtilityEntity to Utility with given count
     *
     * @author Daniil Zavyalov
     */
    fun utilityEntityToModel(entity: UtilityEntity, count: Int): Utility {
        return Utility(entity.id, entity.name, entity.iconUrl, count)
    }

    /**
     * Converts WorkspaceZoneEntity to WorkspaceZone model
     *
     * @author Daniil Zavyalov
     */
    fun zoneEntityToModel(zoneEntity: WorkspaceZoneEntity): WorkspaceZone {
        return WorkspaceZone(zoneEntity.id, zoneEntity.name)
    }

    /**
     * Converts Workspace and WorkspaceTagEntity to WorkspaceEntity with random UUID.
     * Use database connection to find tag
     *
     * @throws InstanceNotFoundException if tag doesn't exist in the database
     *
     * @author Daniil Zavyalov
     */
    fun modelToEntity(model: Workspace): WorkspaceEntity {
        val tagEntity: WorkspaceTagEntity = database.workspaceTags.find { it.name eq model.tag }
            ?: throw InstanceNotFoundException(WorkspaceTagEntity::class, "Workspace tag ${model.tag} not found")

        if(tagEntity.name != model.tag) {
            throw IllegalArgumentException("model.tag=${model.tag} is not equal to tagEntity.name=${tagEntity.name}")
        }
        return WorkspaceEntity {
            id = UUID.randomUUID()
            name = model.name
            tag = tagEntity
            zone = model.zone?.let{ zoneModelToEntity(it) }
        }
    }

    /**
     * Converts WorkspaceZoneEntity to WorkspaceZone model
     *
     * @author Daniil Zavyalov
     */
    fun zoneModelToEntity(zoneModel: WorkspaceZone): WorkspaceZoneEntity {
        return WorkspaceZoneEntity {
            id = zoneModel.id
            name = zoneModel.name
        }
    }
}