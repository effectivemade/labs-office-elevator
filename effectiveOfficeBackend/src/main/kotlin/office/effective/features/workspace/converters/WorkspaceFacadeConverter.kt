package office.effective.features.workspace.converters

import office.effective.common.utils.UuidValidator
import office.effective.dto.UtilityDTO
import office.effective.dto.WorkspaceDTO
import office.effective.dto.WorkspaceZoneDTO
import office.effective.model.Utility
import office.effective.model.Workspace
import office.effective.model.WorkspaceZone
import java.util.*

class WorkspaceFacadeConverter(private val uuidValidator: UuidValidator) {

    /**
     * Converts Workspace with utilities to WorkspaceDTO
     *
     * @author Daniil Zavyalov
     */
    fun modelToDto(model: Workspace): WorkspaceDTO {
        val utilities = model.utilities.map { utilityModelToDto(it) }
        return WorkspaceDTO(
            model.id.toString(), model.name, utilities, model.zone?.let { zoneModelToDto(it) }, model.tag
        )
    }

    /**
     * Converts Utility to UtilityDTO
     *
     * @author Daniil Zavyalov
     */
    private fun utilityModelToDto(model: Utility): UtilityDTO {
        return UtilityDTO(model.id.toString(), model.name, model.iconUrl, model.count)
    }

    /**
     * Converts WorkspaceZone to WorkspaceZoneDTO
     *
     * @author Daniil Zavyalov
     */
    fun zoneModelToDto(model: WorkspaceZone): WorkspaceZoneDTO {
        return WorkspaceZoneDTO(model.id.toString(), model.name)
    }

    /**
     * Converts WorkspaceDTO with utilities to Workspace
     *
     * @author Daniil Zavyalov
     */
    fun dtoToModel(dto: WorkspaceDTO): Workspace {
        var workspaceId: UUID? = null
        if (dto.id != "null") {
            workspaceId = uuidValidator.uuidFromString(dto.id)
        }

        val utilities = dto.utilities.map { utilityDtoToModel(it) }
        return Workspace(id = workspaceId,
            name = dto.name,
            tag = dto.tag,
            utilities = utilities,
            zone = dto.zone?.let { zoneDtoToModel(it) })
    }

    /**
     * Converts UtilityDTO to Utility
     *
     * @author Daniil Zavyalov
     */
    private fun utilityDtoToModel(dto: UtilityDTO): Utility {
        return Utility(uuidValidator.uuidFromString(dto.id), dto.name, dto.iconUrl, dto.count)
    }

    /**
     * Converts WorkspaceZoneDTO to WorkspaceZone
     *
     * @author Daniil Zavyalov
     */
    private fun zoneDtoToModel(dto: WorkspaceZoneDTO): WorkspaceZone {
        return WorkspaceZone(uuidValidator.uuidFromString(dto.id), dto.name)
    }
}