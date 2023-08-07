package office.effective.features.booking.dto

import kotlinx.serialization.Serializable
import office.effective.features.user.dto.UserDTO
import office.effective.features.workspace.dto.WorkspaceDTO

@Serializable
data class BookingDTO (
    var owner: UserDTO,
    var participants: List<UserDTO>,
    var workspace: WorkspaceDTO,
    var id: String?,
    var beginBooking: Long,
    var endBooking: Long
)
