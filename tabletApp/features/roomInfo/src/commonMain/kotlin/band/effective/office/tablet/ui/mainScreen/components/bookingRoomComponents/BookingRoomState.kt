package band.effective.office.tablet.ui.mainScreen.components.bookingRoomComponents

data class BookingRoomState(
    val length: Int,
    val organizer: String,
    val organizers: List<String>
){
    companion object{
        val default = BookingRoomState(
            length = 0,
            organizer = "",
            organizers = listOf()
        )
    }
}
