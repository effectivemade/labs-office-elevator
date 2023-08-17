package office.effective.dto

import kotlinx.serialization.Serializable
import model.RecurrenceDTO
import office.effective.dto.UserDTO
import office.effective.dto.WorkspaceDTO

@Serializable
data class BookingDTO (
    val owner: UserDTO,
    val participants: List<UserDTO>,
    val workspace: WorkspaceDTO,
    val id: String?,
    val beginBooking: Long,
    val endBooking: Long,
    val recurrence: RecurrenceDTO? = null
)
