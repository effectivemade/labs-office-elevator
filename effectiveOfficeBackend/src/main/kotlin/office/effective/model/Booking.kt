package office.effective.model

import java.time.Instant
import java.util.UUID

data class Booking (
    var owner: UserModel,
    var participants: List<UserModel>,
    var workspace: Workspace,
    var id: UUID?,
    var beginBooking: Instant,
    var endBooking: Instant
)