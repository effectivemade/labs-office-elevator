package band.effective.office.elevator.ui.booking.store

import band.effective.office.elevator.MainRes
import band.effective.office.elevator.domain.models.BookingInfo
import band.effective.office.elevator.domain.models.BookingPeriod
import band.effective.office.elevator.domain.models.CreatingBookModel
import band.effective.office.elevator.domain.models.TypeEndPeriodBooking
import band.effective.office.elevator.ui.booking.models.Frequency
import band.effective.office.elevator.ui.booking.models.MockDataSpaces
import band.effective.office.elevator.ui.booking.models.WorkSpaceType
import band.effective.office.elevator.ui.booking.models.WorkSpaceUI
import band.effective.office.elevator.ui.booking.models.WorkspaceZoneUI
import band.effective.office.elevator.ui.models.TypesList
import band.effective.office.elevator.utils.getCurrentDate
import com.arkivanov.mvikotlin.core.store.Store
import com.commandiron.wheel_picker_compose.utils.getCurrentTime
import dev.icerock.moko.resources.StringResource
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

        object CloseRepeatDialog : Intent

        object CloseBookRepeat : Intent
        data class OnSelectBookingPeriod(val pair: Pair<String, BookingPeriod>) : Intent
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
        data class ChangeSelectedWorkSpacesZone(val workspaceZoneUI: List<WorkspaceZoneUI>) : Intent
        data class ApplyDate(val date: List<LocalDate>) : Intent
        data class ShowPlace(val type: String) : Intent

        data class ChangeWorkSpacesUI(val workSpaces: List<WorkSpaceUI>) : Intent
        data class ChangeType(val type: WorkSpaceType) : Intent

        data class ChangeWholeDay(val wholeDay: Boolean) : Intent

        data class ChangeFrequency(val frequency: Frequency) : Intent

        data class ChangeBookingRepeat(val bookingRepeat: StringResource) : Intent

        data class ChangeSelectedType(val selectedType: TypesList) : Intent

        object OpenCalendarForEndDate : Intent

        data class SelectNewDateOfEnd(val date: LocalDate?) : Intent

        object CloseCalendarForEndDate : Intent
    }

    data class State(
        val workSpaces: List<WorkSpaceUI>,
        val workSpacesAll: List<WorkSpaceUI>,
        val creatingBookModel: CreatingBookModel,
        val currentDate: LocalDate,
        val workSpacesType: WorkSpaceType,
        val currentWorkspaceZones: List<WorkspaceZoneUI>,
        val allZonesList: List<WorkspaceZoneUI>,
        val selectedStartDate: LocalDate,
        val selectedStartTime: LocalTime,
        val selectedFinishTime: LocalTime,
        val selectedFinishDate: LocalDate,
        val wholeDay: Boolean,
        val isStart: Boolean,
        val isStartDate: Boolean,
        val frequency: Frequency,
        val repeatBooking: StringResource,
        val bookingPeriod: BookingPeriod,
        val selectedType: TypesList,
        val bookingInfo: BookingInfo,
        val selectedWorkspaceId: String,
        val isLoadingListWorkspaces: Boolean,
        val isLoadingBookingCreation: Boolean,
        val typeOfEnd: TypeEndPeriodBooking,
        val dateOfEndPeriod: LocalDate
    ) {
        companion object {
            val initState = State(
                workSpaces = MockDataSpaces.workSpacesUI,
                workSpacesAll = MockDataSpaces.workSpacesUI,
                currentDate = getCurrentDate(),
                creatingBookModel = CreatingBookModel(
                    workSpaceId = "",
                    dateOfStart = LocalDateTime(getCurrentDate(), getCurrentTime()),
                    dateOfEnd = LocalDateTime(getCurrentDate(), getCurrentTime()),
                    bookingPeriod = BookingPeriod.NoPeriod,
                    typeOfEndPeriod = TypeEndPeriodBooking.Never
                ),
                workSpacesType = WorkSpaceType.WORK_PLACE,
                currentWorkspaceZones = MockDataSpaces.allBookingZone,
                selectedStartDate = getCurrentDate(),
                selectedStartTime = getCurrentTime(),
                selectedFinishTime = getCurrentTime(),
                wholeDay = false,
                isStart = true,
                frequency = Frequency(days = listOf(), researchEnd = Triple(Pair("ThisDay",""),"","")),
                repeatBooking = MainRes.strings.booking_not_repeat,
                bookingPeriod = BookingPeriod.NoPeriod,
                selectedType = TypesList(
                    name = MainRes.strings.workplace,
                    icon = MainRes.images.table_icon,
                    type = WorkSpaceType.WORK_PLACE
                ),
                bookingInfo = BookingInfo(
                    id = "",
                    workSpaceId = "",
                    ownerId = "",
                    seatName = "",
                    dateOfStart = LocalDateTime(date = getCurrentDate(), time = getCurrentTime()),
                    dateOfEnd = LocalDateTime(date = getCurrentDate(), time = getCurrentTime())
                ),
                selectedFinishDate = getCurrentDate(),
                isStartDate = true,
                selectedWorkspaceId = "",
                isLoadingListWorkspaces = true,
                isLoadingBookingCreation = true,
                typeOfEnd = TypeEndPeriodBooking.CountRepeat(1),
                dateOfEndPeriod = getCurrentDate(),
                allZonesList = listOf()
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

        data class ShowToast(val message: String) : Label

        object OpenCalendarForDateOfEnd : Label

        object CloseCalendarForDateOfEnd : Label
    }
}