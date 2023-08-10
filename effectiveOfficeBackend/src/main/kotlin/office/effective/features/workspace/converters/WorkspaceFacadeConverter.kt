package office.effective.features.workspace.converters

import office.effective.common.utils.UuidValidator
import office.effective.features.workspace.dto.UtilityDTO
import office.effective.features.workspace.dto.WorkspaceDTO
import office.effective.features.workspace.dto.WorkspaceZoneDTO
import office.effective.model.Utility
import office.effective.model.Workspace
import office.effective.model.WorkspaceZone

class WorkspaceFacadeConverter(private val uuidValidator: UuidValidator) {

    /**
     * Converts Workspace with utilities to WorkspaceDTO
     *
     * @author Daniil Zavyalov
     */
    fun modelToDto(model: Workspace): WorkspaceDTO {
        val utilities = model.utilities.map { utilityModelToDto(it) }
        return WorkspaceDTO(
            model.id.toString(),
            model.name,
            utilities,
            model.zone?.let { zoneModelToDto(it) }
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
        val utilities = dto.utilities.map { utilityDtoToModel(it) }
        return Workspace(
            id = uuidValidator.uuidFromString(dto.id),
            name = dto.name,
            tag = "", //This is probably wrong
            utilities = utilities,
            zone = dto.zone?.let { zoneDtoToModel(it) }
        )
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