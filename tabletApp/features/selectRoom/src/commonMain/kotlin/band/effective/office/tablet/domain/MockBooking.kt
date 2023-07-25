package band.effective.office.tablet.domain

import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.domain.model.EventInfo
import java.util.Calendar

object MockBooking {
    val bookingCheckTime15min = band.effective.office.tablet.domain.model.Booking(
        nameRoom = "Sirius",
        eventInfo = EventInfo(
            startTime = setTime(2023, 4, 20, 15, 20),
            finishTime = setTime(2023, 4, 20, 15, 35),
            organizer = "Ольга Белозёрова"
        )
    )

    val bookingCheckTime1h = band.effective.office.tablet.domain.model.Booking(
        nameRoom = "Sirius",
        eventInfo = EventInfo(
            startTime = setTime(2023, 11, 11, 15, 20),
            finishTime = setTime(2023, 11, 11, 16, 20),
            organizer = "Ольга Белозёрова"
        )
    )

    val bookingCheckTime1h15min = band.effective.office.tablet.domain.model.Booking(
        nameRoom = "Sirius",
        eventInfo = EventInfo(
            startTime = setTime(2023, 11, 11, 15, 20),
            finishTime = setTime(2023, 11, 11, 16, 35),
            organizer = "Ольга Белозёрова"
        )
    )

    val bookingCheckOrganizer = band.effective.office.tablet.domain.model.Booking(
        nameRoom = "Sirius",
        eventInfo = EventInfo(
            startTime = setTime(2023, 1, 7, 9, 20),
            finishTime = setTime(2023, 1, 7, 11, 35),
            organizer = "Абдурахмангаджи Константинопольский"
        )
    )

    private fun setTime(y: Int, m: Int, d: Int, h: Int, min: Int): Calendar {
        val currentTime = Calendar.getInstance()
        currentTime.set(y, m, d, h, min)
        return currentTime
    }
}