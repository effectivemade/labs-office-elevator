package band.effective.office.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class BookingDTO (
    var owner: UserDTO,
    var participants: List<UserDTO>,
    var workspace: WorkspaceDTO,
    var id: String?,
    var beginBooking: Long,
    var endBooking: Long
)
