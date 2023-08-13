package office.effective.dto

import kotlinx.serialization.Serializable
import office.effective.dto.UserDTO
import office.effective.dto.WorkspaceDTO

@Serializable
data class BookingDTO (
    var owner: UserDTO,
    var participants: List<UserDTO>,
    var workspace: WorkspaceDTO,
    var id: String?,
    var beginBooking: Long,
    var endBooking: Long
)
