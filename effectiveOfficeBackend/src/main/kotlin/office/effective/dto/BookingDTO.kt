package office.effective.dto

import kotlinx.serialization.Serializable
import office.effective.dto.UserDTO
import office.effective.dto.WorkspaceDTO

@Serializable
data class BookingDTO (
    val owner: UserDTO,
    val participants: List<UserDTO>,
    val workspace: WorkspaceDTO,
    val id: String?,
    val beginBooking: Long,
    val endBooking: Long
)
