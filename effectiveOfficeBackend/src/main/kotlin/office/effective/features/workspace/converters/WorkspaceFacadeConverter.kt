package office.effective.features.workspace.converters

import office.effective.common.utils.UuidValidator
import office.effective.features.workspace.dto.UtilityDTO
import office.effective.features.workspace.dto.WorkspaceDTO
import office.effective.model.Utility
import office.effective.model.Workspace

class WorkspaceFacadeConverter(private val uuidValidator: UuidValidator) {
    fun modelToDto(model: Workspace): WorkspaceDTO {
        val utilities = model.utilities.map { utilityModelToDto(it) }
        return WorkspaceDTO(model.id.toString(), model.name, utilities)
    }

    private fun utilityModelToDto(model: Utility): UtilityDTO {
        return UtilityDTO(model.id.toString(), model.name, model.iconUrl, model.count)
    }

    fun dtoToModel(dto: WorkspaceDTO): Workspace {
        val utilities = dto.utilities.map { utilityDtoToModel(it) }
        return Workspace(
            id = uuidValidator.uuidFromString(dto.id),
            name = dto.name,
            tag = "", //Это наверное не правильно
            utilities = utilities
        )
    }

    private fun utilityDtoToModel(dto: UtilityDTO): Utility {
        return Utility(uuidValidator.uuidFromString(dto.id), dto.name, dto.iconUrl, dto.count)
    }
}