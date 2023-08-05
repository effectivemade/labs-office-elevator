package band.effective.office.network.dto

data class BookingInfo(
    val id: String,
    val begin: Long,
    val end: Long,
    val ownerId: String,
    val participants: List<UserDTO>,
    val workspaceId: String
)