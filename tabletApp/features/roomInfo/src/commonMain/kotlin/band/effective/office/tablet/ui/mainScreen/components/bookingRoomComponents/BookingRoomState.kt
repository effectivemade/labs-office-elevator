package band.effective.office.tablet.ui.mainScreen.components.bookingRoomComponents

import java.util.Calendar
import java.util.GregorianCalendar

data class BookingRoomState(
    val length: Int,
    val organizer: String,
    val organizers: List<String>,
    val selectDate: Calendar
){
    companion object{
        val default = BookingRoomState(
            length = 0,
            organizer = "",
            organizers = listOf(),
            selectDate = GregorianCalendar()
        )
    }
}
