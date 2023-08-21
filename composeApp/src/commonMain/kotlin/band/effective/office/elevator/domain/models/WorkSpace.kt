package band.effective.office.elevator.domain.models

import band.effective.office.network.dto.UtilityDTO
import band.effective.office.network.dto.WorkspaceDTO
import band.effective.office.network.dto.WorkspaceZoneDTO

data class WorkSpace(
    val id: String,
    val name: String = "",
    val utilities: List<UtilityDTO> = listOf(),
    val zone: WorkspaceZoneDTO? = null
)

fun WorkSpace.toDTO() =
    WorkspaceDTO(
        id = id,
        name = name,
        utilities = utilities,
        zone = zone
    )