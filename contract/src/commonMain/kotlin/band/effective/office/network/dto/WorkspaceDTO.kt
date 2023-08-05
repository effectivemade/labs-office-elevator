package band.effective.office.network.dto

// TODO(Mishnko Maksim): tablet must get events list in workspace
data class WorkspaceDTO(
    val id: String,
    val name: String,
    val utilities: List<UtilityDTO>
)