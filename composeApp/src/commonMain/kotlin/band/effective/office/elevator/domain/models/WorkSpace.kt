package band.effective.office.elevator.domain.models

import band.effective.office.network.dto.UtilityDTO
import band.effective.office.network.dto.WorkspaceDTO
import band.effective.office.network.dto.WorkspaceZoneDTO

data class WorkSpace(
    val id: String,
    val name: String = "",
    val utilities: List<UtilityDTO> = listOf(),
    val zone: WorkspaceZone? = null
)

data class WorkspaceZone(val id: String, val name: String)

fun WorkspaceZone.toDTO() =
    WorkspaceZoneDTO(id = id, name = name)

fun List<WorkspaceZoneDTO>.toDomain() = map {it.toDomain()}
fun WorkspaceZoneDTO.toDomain() =
    WorkspaceZone(id = id, name = name)

fun WorkSpace.toDTO() =
    WorkspaceDTO(
        id = id,
        name = name,
        utilities = utilities,
        zone = zone?.toDTO()
    )

fun WorkspaceDTO.toDomain() =
    WorkSpace(
        id = id,
        name = name,
        utilities = utilities,
        zone = zone?.toDomain()
    )

fun List<WorkspaceDTO>.toDomain() = map { it.toDomain() }