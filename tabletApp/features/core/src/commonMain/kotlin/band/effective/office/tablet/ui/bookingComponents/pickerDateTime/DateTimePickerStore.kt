package band.effective.office.tablet.ui.bookingComponents.pickerDateTime

import com.arkivanov.mvikotlin.core.store.Store
import java.util.Calendar
import java.util.GregorianCalendar


interface DateTimePickerStore: Store<DateTimePickerStore.Intent, DateTimePickerStore.State, Nothing> {
    sealed interface Intent {
        data class OnSetDate(
            val changedDay: Int,
            val changedMonth: Int,
            val changedYear: Int,
            val changedHour: Int,
            val changedMinute: Int
            ) : Intent
        object OnDateTimePickerModal: Intent
        data class CloseModal(val close: (() -> Unit)? = null) : Intent
    }

    data class State(
        val selectDate: Calendar,
        val currentDate: Calendar,
        val isSelectCurrentTime: Boolean
    ) {
        fun isCorrect() = isCorrectDate()

        fun isCorrectDate() = DateTimePickerValidator.validateDate(selectDate)

        companion object {
            val default = State(
                selectDate = GregorianCalendar(),
                currentDate = GregorianCalendar(),
                isSelectCurrentTime = true
            )
        }
    }

    object DateTimePickerValidator {
        fun validateDate(date: Calendar): Boolean {
            val date = date.clone() as Calendar
            date.add(Calendar.SECOND, 59)
            return date > GregorianCalendar()
        }
    }
}

