package band.effective.office.elevator.ui.booking.store

import band.effective.office.elevator.MainRes
import band.effective.office.elevator.domain.models.BookingInfo
import band.effective.office.elevator.domain.models.BookingPeriodUI
import band.effective.office.elevator.domain.models.CreatingBookModel
import band.effective.office.elevator.domain.models.TypeEndPeriodBooking
import band.effective.office.elevator.ui.booking.models.Frequency
import band.effective.office.elevator.ui.booking.models.WorkSpaceType
import band.effective.office.elevator.ui.booking.models.WorkSpaceUI
import band.effective.office.elevator.ui.booking.models.WorkSpaceZone
import band.effective.office.elevator.ui.models.TypesList
import band.effective.office.elevator.utils.getCurrentDate
import com.arkivanov.mvikotlin.core.store.Store
import com.commandiron.wheel_picker_compose.utils.getCurrentTime
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

interface BookingStore : Store<BookingStore.Intent, BookingStore.State, BookingStore.Label> {

    sealed interface Intent {
        object OpenChooseZone : Intent
        data class OpenStartTimeModal(val isStart: Boolean, val time: LocalTime) : Intent
        object OpenFinishTimeModal : Intent
        object CloseFinishTimeModal : Intent
        object CloseStartTimeModal : Intent
        data class ApplyTime(val isStart: Boolean, val time: LocalTime) : Intent
        object CloseChooseZone : Intent
        object OpenRepeatDialog : Intent
        object CloseBookRepeat : Intent
        data class OpenBookRepeat(val pair: Pair<String, BookingPeriodUI>) : Intent
        data class OpenBookAccept(val value: WorkSpaceUI) : Intent
        object CloseBookAccept : Intent
        object OpenBookPeriod : Intent
        object OpenConfirmBooking : Intent
        object SearchSuitableOptions : Intent
        object CloseBookPeriod : Intent
        object OpenCalendar : Intent

        object CloseCalendar : Intent
        object OpenMainScreen : Intent
        object CloseConfirmBooking : Intent
        data class ChangeSelectedWorkSpacesZone(val workSpaceZone: List<WorkSpaceZone>) : Intent
        data class ApplyDate(val date: LocalDate?) : Intent
        data class ShowPlace(val type: String) : Intent

        data class ChangeWorkSpacesUI(val workSpaces: List<WorkSpaceUI>) : Intent
        data class ChangeType(val type: WorkSpaceType) : Intent

        data class ChangeWholeDay(val wholeDay: Boolean) : Intent

        data class ChangeFrequency(val frequency: Frequency) : Intent

        data class ChangeBookingRepeat(val bookingRepeat: String) : Intent

        data class ChangeSelectedType(val selectedType: TypesList) : Intent
    }

    data class State(
        val workSpaces: List<WorkSpaceUI>,
        val creatingBookModel: CreatingBookModel,
        val currentDate: LocalDate,
        val workSpacesType: WorkSpaceType,
        val workSpacesZone: List<WorkSpaceZone>,
        val selectedStartDate: LocalDate,
        val selectedStartTime: LocalTime,
        val selectedFinishTime: LocalTime,
        val wholeDay: Boolean,
        val isStart: Boolean,
        val frequency: Frequency,
        val repeatBooking: String,
        val bookingPeriodUI: BookingPeriodUI,
        val selectedType: TypesList,
        val bookingInfo: BookingInfo
    ) {
        companion object {
            val initState = State(
                workSpaces = workSpacesUI,
                currentDate = getCurrentDate(),
                creatingBookModel = CreatingBookModel(
                    workSpaceId = "",
                    dateOfStart = LocalDateTime(getCurrentDate(), getCurrentTime()),
                    dateOfEnd = LocalDateTime(getCurrentDate(), getCurrentTime()),
                    bookingPeriodUI = BookingPeriodUI.NoPeriod,
                    typeOfEndPeriod = TypeEndPeriodBooking.Never
                ),
                workSpacesType = WorkSpaceType.WORK_PLACE,
                workSpacesZone = allBookingZone,
                selectedStartDate = getCurrentDate(),
                selectedStartTime = getCurrentTime(),
                selectedFinishTime = getCurrentTime(),
                wholeDay = false,
                isStart = true,
                frequency = Frequency(days = listOf()),
                repeatBooking = "Бронирование не повторяется",
                bookingPeriodUI = BookingPeriodUI.NoPeriod,
                selectedType = TypesList(
                    name = MainRes.strings.workplace,
                    icon = MainRes.images.table_icon,
                    type = WorkSpaceType.WORK_PLACE
                ),
                bookingInfo = BookingInfo(
                    id = "",
                    ownerId = "",
                    seatName = "",
                    dateOfStart = LocalDateTime(date = getCurrentDate(), time = getCurrentTime()),
                    dateOfEnd = LocalDateTime(date = getCurrentDate(), time = getCurrentTime())
                )
            )
        }
    }

    sealed interface Label {
        object OpenChooseZone : Label
        object OpenConfirmBooking : Label
        object CloseChooseZone : Label
        object OpenBookPeriod : Label
        object CloseBookPeriod : Label
        object CloseRepeatDialog : Label
        object OpenRepeatDialog : Label
        data class OpenBookAccept(val value: WorkSpaceUI) : Label
        object CloseBookAccept : Label
        object CloseCalendar : Label

        object OpenCalendar : Label
        object CloseConfirmBooking : Label
        object OpenStartTimeModal : Label
        object OpenFinishTimeModal : Label
        object CloseFinishTimeModal : Label
        object CloseStartTimeModal : Label
        object CloseBookRepeat : Label
        object OpenBookRepeat : Label
    }
}

//TODO(Artem Gruzdev) replace that
//TODO(Slava) replace with what?
val allBookingZone = listOf(
    WorkSpaceZone(name = "Sirius", isSelected = true),
    WorkSpaceZone(name = "Antares", isSelected = true),
    WorkSpaceZone(name = "Mars", isSelected = true),
    WorkSpaceZone(name = "Cassiopeia", isSelected = true),
    WorkSpaceZone(name = "Arrakis", isSelected = true),
)

val allMeetingRooms = listOf(
    WorkSpaceZone(name = "Moon", isSelected = true),
    WorkSpaceZone(name = "Sun", isSelected = true),
    WorkSpaceZone(name = "Mercury", isSelected = true),
    WorkSpaceZone(name = "Pluto", isSelected = true),
    WorkSpaceZone(name = "Sun", isSelected = true)
)

val workSpacesUI = listOf(
    WorkSpaceUI(
        workSpaceId = "",
        workSpaceName = "Cassiopeia",
        workSpaceType = WorkSpaceType.MEETING_ROOM
    ),
    WorkSpaceUI(
        workSpaceId = "",
        workSpaceName = "Arrakis",
        workSpaceType = WorkSpaceType.MEETING_ROOM
    ),
    WorkSpaceUI(
        workSpaceId = "",
        workSpaceName = "Mars",
        workSpaceType = WorkSpaceType.MEETING_ROOM
    ),
    WorkSpaceUI(
        workSpaceId = "",
        workSpaceName = "Antares",
        workSpaceType = WorkSpaceType.MEETING_ROOM
    ),
    WorkSpaceUI(
        workSpaceId = "",
        workSpaceName = "Sirius",
        workSpaceType = WorkSpaceType.MEETING_ROOM
    ),

    WorkSpaceUI(
        workSpaceId = "",
        workSpaceName = "Moon",
        workSpaceType = WorkSpaceType.WORK_PLACE
    ),
    WorkSpaceUI(
        workSpaceId = "",
        workSpaceName = "Sun",
        workSpaceType = WorkSpaceType.WORK_PLACE
    ),
    WorkSpaceUI(
        workSpaceId = "",
        workSpaceName = "Mercury",
        workSpaceType = WorkSpaceType.WORK_PLACE
    ),
    WorkSpaceUI(
        workSpaceId = "",
        workSpaceName = "Pluto",
        workSpaceType = WorkSpaceType.WORK_PLACE
    )
)