package office.effective.features.workspace.converters

import office.effective.features.workspace.dto.UtilityDTO
import office.effective.features.workspace.dto.WorkspaceDTO
import office.effective.model.Utility
import office.effective.model.Workspace

class WorkspaceFacadeConverter {
    fun workspaceModelToDto(model: Workspace): WorkspaceDTO {
        val utilities = model.utilities.map { utilityModelToDto(it) }
        return WorkspaceDTO(model.id.toString(), model.name, utilities)
    }

    private fun utilityModelToDto(model: Utility): UtilityDTO {
        return UtilityDTO(model.id.toString(), model.name, model.iconUrl, model.count)
    }
}