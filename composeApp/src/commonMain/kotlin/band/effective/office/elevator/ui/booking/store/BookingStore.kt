package band.effective.office.elevator.ui.booking.store

import band.effective.office.elevator.domain.models.BookingPeriod
import band.effective.office.elevator.domain.models.CreatingBookModel
import band.effective.office.elevator.domain.models.TypeEndPeriodBooking
import band.effective.office.elevator.ui.booking.models.WorkSpaceType
import band.effective.office.elevator.ui.booking.models.WorkSpaceUI
import band.effective.office.elevator.ui.booking.models.WorkSpaceZone
import band.effective.office.elevator.utils.getCurrentDate
import com.arkivanov.mvikotlin.core.store.Store
import com.commandiron.wheel_picker_compose.utils.getCurrentTime
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

interface BookingStore: Store<BookingStore.Intent,BookingStore.State, BookingStore.Label> {

    sealed interface Intent {
        object OpenChooseZone: Intent
        object OpenTimeModal: Intent
        object CloseTimeModal: Intent
        data class ApplyTime(val time: LocalTime) : Intent
        object CloseChooseZone: Intent
        object OpenRepeatDialog : Intent
        object CloseBookRepeat : Intent
        object OpenBookRepeat : Intent
        object OpenBookAccept: Intent
        object CloseBookAccept: Intent
        object OpenBookPeriod: Intent
        object OpenConfirmBooking: Intent
        object SearchSuitableOptions: Intent
        object CloseBookPeriod: Intent
        object OpenCalendar : Intent

        object CloseCalendar : Intent
        object OpenMainScreen: Intent
        object CloseConfirmBooking: Intent
        data class ChangeSelectedWorkSpacesZone(val workSpaceZone: List<WorkSpaceZone>) : Intent
        data class ApplyDate(val date: LocalDate?) : Intent
        data class ShowPlace(val type:String): Intent
    }

    data class State(
        val workSpaces : List<WorkSpaceUI>,
        val creatingBookModel: CreatingBookModel,
        val currentDate: LocalDate,
        val workSpacesType: WorkSpaceType,
        val workSpacesZone: List<WorkSpaceZone>
    ){
        companion object {
            val initState = State(
                workSpaces = listOf(),
                currentDate = getCurrentDate(),
                creatingBookModel = CreatingBookModel(
                    workSpaceId = "",
                    dateOfStart = LocalDateTime(getCurrentDate(), getCurrentTime()),
                    dateOfEnd = LocalDateTime(getCurrentDate(), getCurrentTime()),
                    bookingPeriod = BookingPeriod.NoPeriod,
                    typeOfEndPeriod = TypeEndPeriodBooking.Never
                ),
                workSpacesType = WorkSpaceType.WORK_PLACE,
                workSpacesZone = allBookingZone
            )
        }
    }

    sealed interface Label{
        object OpenChooseZone : Label
        object OpenConfirmBooking: Label
        object CloseChooseZone : Label
        object OpenBookPeriod: Label
        object CloseBookPeriod: Label
        object CloseRepeatDialog : Label
        object OpenRepeatDialog : Label
        object OpenBookAccept: Label
        object CloseBookAccept: Label
        object CloseCalendar : Label

        object OpenCalendar : Label
        object CloseConfirmBooking: Label
        object OpenTimeModal: Label
        object CloseTimeModal: Label
        object CloseBookRepeat : Label
        object OpenBookRepeat : Label
    }
}

//TODO(Artem Gruzdev) replace that
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