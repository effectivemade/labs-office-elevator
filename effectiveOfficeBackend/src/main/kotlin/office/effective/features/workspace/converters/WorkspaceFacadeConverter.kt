package office.effective.features.workspace.converters

import office.effective.common.utils.UuidValidator
import office.effective.features.workspace.dto.UtilityDTO
import office.effective.features.workspace.dto.WorkspaceDTO
import office.effective.model.Utility
import office.effective.model.Workspace

class WorkspaceFacadeConverter(private val uuidValidator: UuidValidator) {

    /**
     * Converts Workspace with utilities to WorkspaceDTO
     *
     * @author Daniil Zavyalov
     */
    fun modelToDto(model: Workspace): WorkspaceDTO {
        val utilities = model.utilities.map { utilityModelToDto(it) }
        return WorkspaceDTO(model.id.toString(), model.name, utilities)
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
            utilities = utilities
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
}