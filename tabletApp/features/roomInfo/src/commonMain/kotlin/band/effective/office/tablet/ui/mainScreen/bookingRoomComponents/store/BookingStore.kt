package band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.store

import band.effective.office.tablet.domain.model.EventInfo
import com.arkivanov.mvikotlin.core.store.Store
import java.util.Calendar
import java.util.GregorianCalendar

interface BookingStore : Store<BookingStore.Intent, BookingStore.State, Nothing> {
    sealed interface Intent {
        object OnBookingCurrentRoom : Intent
        object OnBookingOtherRoom : Intent
        data class OnChangeDate(val changeInDay: Int) : Intent
        data class OnChangeLength(val change: Int) : Intent
        data class OnChangeOrganizer(val newOrganizer: String) : Intent
    }

    data class State(
        val length: Int,
        val organizer: String,
        val organizers: List<String>,
        val selectDate: Calendar,
        val isBusy: Boolean,
        val busyEvent: EventInfo,
        val roomName: String
    ) {
        fun isCorrect() = isCorrectOrganizer() && isCorrectLength() && isCorrectDate()

        fun isCorrectOrganizer() = BookingInfoValidator.validateOrganizer(organizer)
        fun isCorrectLength() = BookingInfoValidator.validateLength(length)
        fun isCorrectDate() = BookingInfoValidator.validateDate(selectDate)

        companion object {
            val default = State(
                length = 0,
                organizer = "",
                organizers = listOf(),
                selectDate = GregorianCalendar(),
                isBusy = false,
                busyEvent = EventInfo.emptyEvent,
                roomName = "Sirius"
            )
        }
    }

    object BookingInfoValidator {
        fun validateDate(date: Calendar): Boolean {
            val date = date.clone() as Calendar
            date.add(Calendar.SECOND, 59)
            return date > GregorianCalendar()
        }

        fun validateLength(length: Int) = length > 0

        fun validateOrganizer(organizer: String) = organizer != ""
    }
}