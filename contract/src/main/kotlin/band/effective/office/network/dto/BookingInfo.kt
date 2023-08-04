package band.effective.office.network.dto

data class BookingInfo(
    val id: String,
    val begin: Long,
    val end: Long,
    val owner: UserDTO,
    val participants: List<UserDTO>
)