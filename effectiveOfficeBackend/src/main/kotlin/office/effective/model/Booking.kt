package office.effective.model

import model.RecurrenceDTO
import java.time.Instant
import java.util.UUID

data class Booking (
    var owner: UserModel,
    var participants: List<UserModel>,
    var workspace: Workspace,
    var id: String?,
    var beginBooking: Instant,
    var endBooking: Instant,
    var recurrence: RecurrenceModel?
)