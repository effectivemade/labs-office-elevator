package band.effective.office.elevator.ui.booking.store

import band.effective.office.elevator.MainRes
import band.effective.office.elevator.domain.entity.BookingInteractor
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
import band.effective.office.elevator.utils.stackOf
import band.effective.office.network.model.Either
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import com.commandiron.wheel_picker_compose.utils.getCurrentTime
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.atTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import dev.icerock.moko.resources.StringResource
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.datetime.minus

class BookingStoreFactory(private val storeFactory: StoreFactory) : KoinComponent {

    private val bookingInteractor: BookingInteractor by inject()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): BookingStore =
        object : BookingStore,
            Store<BookingStore.Intent, BookingStore.State, BookingStore.Label> by storeFactory.create(
                name = "BookingStore",
                initialState = BookingStore.State.initState,
                bootstrapper = coroutineBootstrapper {
                    launch {
                        dispatch(Action.InitWorkSpaces)
                    }
                },
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Msg {
        data class UpdateAllZones(val zones: List<WorkspaceZoneUI>) : Msg
        data class ChangeTypeOfEnd(val type: TypeEndPeriodBooking) : Msg
        data class BeginningBookingTime(val time: LocalTime) : Msg
        data class BeginningBookingDate(val date: LocalDate) : Msg
        data class EndBookingTime(val time: LocalTime) : Msg
        data class EndBookingDate(val date: LocalDate) : Msg
        data class SelectedTypeList(val type: TypesList) : Msg
        data class DateBooking(val date: LocalDate) : Msg
        data class TimeBooking(val time: LocalTime) : Msg

        data class ChangeSelectedWorkSpacesZone(val workSpacesZone: List<WorkspaceZoneUI>) : Msg

        data class ChangeWorkSpacesUI(val workSpacesUI: List<WorkSpaceUI>) : Msg

        data class WholeDay(val wholeDay: Boolean) : Msg
        data class IsStartTimePicked(val isStart: Boolean) : Msg
        data class ChangeFrequency(val frequency: Frequency) : Msg
        data class ChangeBookingRepeat(val bookingRepeat: StringResource) : Msg

        data class ChangeBookingRepeatAndTypeOfEnd(
            val bookingPeriod: BookingPeriod,
            val typeEndPeriodBooking: TypeEndPeriodBooking
        ) : Msg

        data class ChangeBookingPeriod(val bookingPeriod: BookingPeriod) : Msg
        data class ChangeWorkingUI(val bookingInfo: BookingInfo) : Msg

        data class IsStartDatePicker(val isStart: Boolean) : Msg

        data class UpdateSelectedWorkspace(val workspaceId: String) : Msg

        data class ChangeLoadingWorkspace(val isLoading: Boolean) : Msg

        data class IsLoadingBookingCreation(val isLoadingBookingCreation: Boolean) : Msg

        data class ChangeDateOfEndPeriod(val date: LocalDate) : Msg

        data class UpdateErrorCreatingBooking(val isError: Boolean) : Msg
    }

    private sealed interface Action {
        object InitWorkSpaces : Action
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<BookingStore.Intent, Action, BookingStore.State, Msg, BookingStore.Label>() {
        override fun executeIntent(
            intent: BookingStore.Intent,
            getState: () -> BookingStore.State
        ) {
            when (intent) {
                is BookingStore.Intent.ShowPlace -> dispatch(
                    Msg.SelectedTypeList(
                        type = TypesList(
                            name = MainRes.strings.app_name,
                            icon = MainRes.images.table_icon,
                            type = WorkSpaceType.WORK_PLACE
                        )
                    )
                )

                is BookingStore.Intent.OpenChooseZone -> {
                    scope.launch {
                        publish(BookingStore.Label.OpenChooseZone)
                    }
                }

                is BookingStore.Intent.CloseChooseZone -> {
                    scope.launch {
                        publish(BookingStore.Label.CloseChooseZone)
                    }
                }

                is BookingStore.Intent.OpenBookPeriod -> {
                    scope.launch {
                        publish(BookingStore.Label.OpenBookPeriod)
                    }
                }

                is BookingStore.Intent.CloseBookPeriod -> {
                    scope.launch {
                        publish(BookingStore.Label.CloseBookPeriod)
                    }
                }

                is BookingStore.Intent.OpenRepeatDialog -> {
                    scope.launch {
                        publish(BookingStore.Label.OpenRepeatDialog)
                    }
                }

                is BookingStore.Intent.OpenBookAccept -> {
                    scope.launch {
                        publish(BookingStore.Label.OpenBookAccept(value = intent.value))
                        with(intent.value) {
                            dispatch(Msg.UpdateSelectedWorkspace(workspaceId = workSpaceId))
                            dispatch(
                                Msg.ChangeWorkingUI(
                                    bookingInfo = BookingInfo(
                                        id = "",
                                        workSpaceId = workSpaceId,
                                        ownerId = "",
                                        seatName = workSpaceName,
                                        dateOfEnd = LocalDateTime(
                                            date = getState().selectedStartDate,
                                            time = getState().selectedFinishTime
                                        ),
                                        dateOfStart = LocalDateTime(
                                            date = getState().selectedStartDate,
                                            time = getState().selectedStartTime
                                        )
                                    )
                                )
                            )
                        }
                    }
                }

                is BookingStore.Intent.CloseBookAccept -> {
                    scope.launch {
                        publish(BookingStore.Label.CloseBookAccept)
                    }
                }

                is BookingStore.Intent.OpenCalendar -> {
                    scope.launch {
                        publish(BookingStore.Label.OpenCalendar)
                    }
                }

                is BookingStore.Intent.CloseCalendar -> {
                    scope.launch {
                        publish(BookingStore.Label.CloseCalendar)
                    }
                }

                is BookingStore.Intent.ApplyDate -> {
                    if (intent.date.isEmpty()) return

                    val startDate = intent.date.first()
                    val endDate = intent.date.last()
                    val datePeriod = endDate - startDate

                    scope.launch {
                        val currentDate = getCurrentDate()
                        publish(BookingStore.Label.CloseCalendar)
                        if (startDate >= currentDate) {
                            dispatch(Msg.BeginningBookingDate(date = startDate))
                        } else {
                            publish(BookingStore.Label.ShowToast("Некорректная дата"))
                        }
                        dispatch(
                            Msg.EndBookingDate(endDate)
                        )
                        if (startDate != endDate)
                            dispatch(
                                Msg.ChangeBookingRepeatAndTypeOfEnd(
                                    bookingPeriod = BookingPeriod.Day,
                                    typeEndPeriodBooking = TypeEndPeriodBooking.CountRepeat(
                                        datePeriod.days + 1
                                    )
                                )
                            )
                        else if (getState().bookingPeriod is BookingPeriod.Day)
                            dispatch(
                                Msg.ChangeBookingRepeatAndTypeOfEnd(
                                    bookingPeriod = BookingPeriod.NoPeriod,
                                    typeEndPeriodBooking = TypeEndPeriodBooking.CountRepeat(1)
                                )
                            )
                    }
                }

                is BookingStore.Intent.OpenConfirmBooking -> {
                    scope.launch {
                        dispatch(Msg.IsLoadingBookingCreation(isLoadingBookingCreation = true))
                        publish(BookingStore.Label.OpenConfirmBooking)

                        val startDate = getState().selectedStartDate
                        val endDate = getState().selectedFinishDate

                        Napier.d { "book period: ${getState().bookingPeriod is BookingPeriod.EveryWorkDay}" }
                        bookingInteractor.create(
                            creatingBookModel = CreatingBookModel(
                                workSpaceId = getState().selectedWorkspaceId, //TODO(Replace with value from DB)
                                dateOfStart = startDate.atTime(getState().selectedStartTime),
                                dateOfEnd = if (endDate != startDate)
                                    startDate.atTime(getState().selectedFinishTime)
                                else
                                    endDate.atTime(getState().selectedFinishTime),
                                bookingPeriod = getState().bookingPeriod,
                                typeOfEndPeriod = getState().typeOfEnd
                            )
                        ).collect { response ->
                            dispatch(Msg.IsLoadingBookingCreation(isLoadingBookingCreation = false))
                            when(response) {
                                is Either.Success -> dispatch(Msg.UpdateErrorCreatingBooking(false))

                                is Either.Error -> dispatch(Msg.UpdateErrorCreatingBooking(true))
                            }
                        }

                    }
                    scope.launch {
                        publish(BookingStore.Label.CloseBookAccept)
                    }
                }

                is BookingStore.Intent.OpenMainScreen -> {
                    //TODO()
                }

                is BookingStore.Intent.CloseConfirmBooking -> {
                    scope.launch {
                        publish(BookingStore.Label.CloseConfirmBooking)
                    }

                }

                is BookingStore.Intent.ApplyTime -> {
                    val currentTime = getCurrentTime()
                    val currentDate = getCurrentDate()
                    val dateOfStart = getState().selectedStartDate

                    val newTime = intent.time
                    scope.launch {
                        if (intent.isStart) {
                            if (currentDate == dateOfStart) {
                                if (newTime >= currentTime)
                                    dispatch(Msg.BeginningBookingTime(intent.time))
                                else
                                    publish(BookingStore.Label.ShowToast("Некорретное время"))
                            } else {
                                dispatch(Msg.BeginningBookingTime(intent.time))
                            }

                        } else {
                            val dateOfEnd = getState().selectedFinishDate
                            val timeOfStart = getState().selectedStartTime

                            if (dateOfStart == dateOfEnd) {
                                if (newTime > timeOfStart) dispatch(Msg.EndBookingTime(intent.time))
                                else publish(BookingStore.Label.ShowToast("Некорретное время"))
                            } else {
                                dispatch(Msg.EndBookingTime(intent.time))
                            }
                        }
                        publish(BookingStore.Label.CloseStartTimeModal)
                    }
                }

                is BookingStore.Intent.CloseStartTimeModal -> {
                    scope.launch {
                        publish(BookingStore.Label.CloseStartTimeModal)
                    }

                }

                is BookingStore.Intent.OpenStartTimeModal -> {
                    scope.launch {
                        dispatch(Msg.IsStartTimePicked(isStart = intent.isStart))
                        publish(BookingStore.Label.OpenStartTimeModal)
                    }

                }

                is BookingStore.Intent.SearchSuitableOptions -> {

                    scope.launch {
                        publish(BookingStore.Label.CloseBookPeriod)
                        getSpacesUI(
                            workspaceZoneUI = getState().currentWorkspaceZones,
                            state = getState(),
                            dispatch = { dispatch(Msg.ChangeWorkSpacesUI(it)) }
                        )
                    }
                }

                is BookingStore.Intent.OnSelectBookingPeriod -> {
                    publish(BookingStore.Label.CloseRepeatDialog)
                    with(intent.pair) {
                        if (second == BookingPeriod.Another)
                            publish(BookingStore.Label.OpenBookRepeat)
                        val name = when (second) {
                            is BookingPeriod.EveryWorkDay -> MainRes.strings.every_work_day
                            is BookingPeriod.Month -> MainRes.strings.every_month
                            is BookingPeriod.NoPeriod -> MainRes.strings.booking_not_repeat
                            is BookingPeriod.Week -> MainRes.strings.every_week
                            is BookingPeriod.Year -> MainRes.strings.every_month
                            is BookingPeriod.Another -> MainRes.strings.another
                            BookingPeriod.Day -> MainRes.strings.another
                        }
                        dispatch(Msg.ChangeBookingRepeat(bookingRepeat = name))
                        dispatch(Msg.ChangeTypeOfEnd(TypeEndPeriodBooking.CountRepeat(10))) // TODO(Artem Gruzdev) backend should fix this
                        Napier.d { "book period check: ${intent.pair.second}" }
                        dispatch(Msg.ChangeBookingPeriod(bookingPeriod = intent.pair.second))
                    }
                }

                is BookingStore.Intent.CloseBookRepeat -> {
                    publish(BookingStore.Label.CloseBookRepeat)
                    publish(BookingStore.Label.OpenBookPeriod)
                }

                is BookingStore.Intent.ChangeSelectedWorkSpacesZone -> {
                    scope.launch {
                        getSpacesUI(
                            workspaceZoneUI = intent.workspaceZoneUI,
                            state = getState(),
                            dispatch = {
                                dispatch(Msg.ChangeWorkSpacesUI(it))
                                dispatch(Msg.ChangeSelectedWorkSpacesZone(intent.workspaceZoneUI))
                            }
                        )
                    }
                }

                is BookingStore.Intent.ChangeWorkSpacesUI -> {
                    intent.workSpaces.forEachIndexed { index1, workSpaceUI ->
                        getState().currentWorkspaceZones.forEachIndexed { index2, workSpaceZone ->
                            intent.workSpaces.filter { workSpaceUI -> workSpaceUI.workSpaceName == workSpaceZone.name }
                        }
                    }
                    dispatch(Msg.ChangeWorkSpacesUI(workSpacesUI = intent.workSpaces))
                }

                is BookingStore.Intent.ChangeType -> {

                }

                is BookingStore.Intent.ChangeWholeDay -> {
                    val dateOfStart = getState().selectedStartDate
                    val dateOfEnd = getState().selectedFinishDate
                    if (dateOfStart == dateOfEnd) {
                        dispatch(Msg.WholeDay(wholeDay = intent.wholeDay))
                        dispatch(Msg.BeginningBookingTime(time = LocalTime(hour = 8, minute = 0)))
                        dispatch(Msg.EndBookingTime(time = LocalTime(hour = 20, minute = 0)))
                    } else {
                        publish(BookingStore.Label.ShowToast("У вас выбраны разные даты"))
                    }
                }

                BookingStore.Intent.OpenFinishTimeModal -> {
                    publish(BookingStore.Label.OpenFinishTimeModal)
                }

                BookingStore.Intent.CloseFinishTimeModal -> {
                    publish(BookingStore.Label.CloseFinishTimeModal)
                }

                is BookingStore.Intent.ChangeFrequency -> {
                    Napier.d { "Close book repeat" }
                    publish(BookingStore.Label.CloseBookRepeat)

                        val typeOfEnd = when(intent.typeOfEnd) {
                            is TypeEndPeriodBooking.DatePeriodEnd -> {
                                // TODO: hen backend fix until date, it`s can be removed
                                val dateRange = intent.typeOfEnd.date - getState().selectedStartDate
                                var countRepeatEvent = 1
                                val timeUnit = when(val period = intent.bookingPeriod) {
                                    is BookingPeriod.Week -> {
                                        countRepeatEvent = period.selectedDayOfWeek.size
                                        7
                                    }
                                    is BookingPeriod.EveryWorkDay ->{
                                        countRepeatEvent = 5
                                        7
                                    }
                                    is BookingPeriod.Year -> 365
                                    is BookingPeriod.Month -> 30
                                    BookingPeriod.Another -> 1
                                    BookingPeriod.Day -> 1
                                    BookingPeriod.NoPeriod -> 1
                                }
                                val resultCount = (dateRange.days / timeUnit + 1) * countRepeatEvent
                                TypeEndPeriodBooking.CountRepeat(resultCount)
                            }
                            TypeEndPeriodBooking.Never -> TypeEndPeriodBooking.CountRepeat(10)
                            else  -> intent.typeOfEnd
                        }
                        dispatch(Msg.ChangeBookingRepeatAndTypeOfEnd(
                            bookingPeriod = intent.bookingPeriod,
                            typeEndPeriodBooking = typeOfEnd
                        ))
                }

                is BookingStore.Intent.ChangeBookingRepeat -> {
                    dispatch(Msg.ChangeBookingRepeat(bookingRepeat = intent.bookingRepeat))
                }

                is BookingStore.Intent.ChangeSelectedType -> {

                    val zones = when (intent.selectedType.type) {
                        WorkSpaceType.WORK_PLACE -> getState().allZonesList
                        WorkSpaceType.MEETING_ROOM -> MockDataSpaces.allMeetingRooms
                    }
                    scope.launch {
                        getSpacesUI(
                            workspaceZoneUI = zones,
                            state = getState(),
                            dispatch = {
                                dispatch(Msg.ChangeSelectedWorkSpacesZone(zones))
                                dispatch(Msg.ChangeWorkSpacesUI(it))
                            }
                        )
                    }
                    dispatch(Msg.SelectedTypeList(type = intent.selectedType))
                }

                BookingStore.Intent.CloseRepeatDialog -> {
                    publish(BookingStore.Label.CloseRepeatDialog)
                }

                BookingStore.Intent.OpenCalendarForEndDate -> {
                    publish(BookingStore.Label.OpenCalendarForDateOfEnd)
                }

                is BookingStore.Intent.SelectNewDateOfEnd -> {

                    publish(BookingStore.Label.CloseCalendarForDateOfEnd)

                    intent.date?.let {
                        dispatch(Msg.ChangeDateOfEndPeriod(it))
                    }
                }

                BookingStore.Intent.CloseCalendarForEndDate ->
                    publish(BookingStore.Label.CloseCalendarForDateOfEnd)
            }
        }

        override fun executeAction(action: Action, getState: () -> BookingStore.State) {
            when (action) {
                Action.InitWorkSpaces -> {
                    scope.launch {
                        getSpacesUI(
                            workspaceZoneUI = getState().currentWorkspaceZones,
                            state = getState(),
                            dispatch = { dispatch(Msg.ChangeWorkSpacesUI(it)) }
                        )
                    }
                    scope.launch {
                        initZones()
                    }
                }
            }
        }

        private suspend fun initZones() {
            withContext(Dispatchers.IO) {
                bookingInteractor.getZones().collect { zonesResponse ->
                    withContext(Dispatchers.Main) {
                        when (zonesResponse) {
                            is Either.Success -> {
                                val zones: List<WorkspaceZoneUI> = zonesResponse.data
                                dispatch(Msg.UpdateAllZones(zones = zones))
                            }

                            is Either.Error -> {
                                //TODO(Artem Gruzdev) schedule error
                            }
                        }
                    }
                }
            }
        }

        private suspend fun getSpacesUI(
            workspaceZoneUI: List<WorkspaceZoneUI>,
            state: BookingStore.State,
            dispatch: (List<WorkSpaceUI>) -> Unit,
        ) {

            dispatch(Msg.ChangeLoadingWorkspace(isLoading = true))

            withContext(Dispatchers.IO) {
                bookingInteractor.getWorkspaces(
                    tag = state.workSpacesType.type,
                    freeFrom = LocalDateTime(
                        date = state.selectedStartDate,
                        time = state.selectedStartTime
                    ),
                    freeUntil = LocalDateTime(
                        date = state.selectedFinishDate,
                        time = state.selectedFinishTime,
                    ),
                    selectedWorkspacesZone = workspaceZoneUI
                ).collect { response ->
                    withContext(Dispatchers.Main) {
                        when (response) {
                            is Either.Success -> {
                                dispatch(response.data)
                            }

                            is Either.Error -> {
                                dispatch(listOf())
                                // TODO SHOW ERROR ON UI
                            }
                        }
                    }
                }

            }
        }
    }

    private object ReducerImpl : Reducer<BookingStore.State, Msg> {
        override fun BookingStore.State.reduce(msg: Msg): BookingStore.State {
            return when (msg) {
                is Msg.ChangeSelectedWorkSpacesZone -> copy(currentWorkspaceZones = msg.workSpacesZone)
                is Msg.DateBooking -> copy(selectedStartDate = msg.date)
                is Msg.TimeBooking -> copy(selectedStartTime = msg.time)
                is Msg.SelectedTypeList -> copy(
                    workSpacesType = msg.type.type,
                    selectedType = msg.type
                )

                is Msg.ChangeWorkSpacesUI -> copy(
                    isLoadingListWorkspaces = false,
                    workSpaces = msg.workSpacesUI
                )

                is Msg.WholeDay -> copy(wholeDay = !msg.wholeDay)
                is Msg.BeginningBookingTime -> copy(selectedStartTime = msg.time)
                is Msg.EndBookingTime -> copy(selectedFinishTime = msg.time)
                is Msg.IsStartTimePicked -> copy(isStart = msg.isStart)
                is Msg.BeginningBookingDate -> copy(selectedStartDate = msg.date)
                is Msg.ChangeFrequency -> {
                    val frequency = msg.frequency
                    copy(
                        bookingPeriod = frequency.getBookPeriod(),
                        typeOfEnd = frequency.getTypeOfEndBooking()
                    )
                }

                is Msg.ChangeBookingRepeat -> copy(repeatBooking = msg.bookingRepeat)
                is Msg.ChangeBookingPeriod -> copy(bookingPeriod = msg.bookingPeriod)
                is Msg.ChangeWorkingUI -> copy(bookingInfo = msg.bookingInfo)
                is Msg.EndBookingDate -> copy(selectedFinishDate = msg.date)
                is Msg.IsStartDatePicker -> copy(isStartDate = msg.isStart)
                is Msg.UpdateSelectedWorkspace -> copy(selectedWorkspaceId = msg.workspaceId)
                is Msg.ChangeLoadingWorkspace -> copy(isLoadingListWorkspaces = msg.isLoading)
                is Msg.IsLoadingBookingCreation -> copy(isLoadingBookingCreation = msg.isLoadingBookingCreation)
                is Msg.ChangeBookingRepeatAndTypeOfEnd ->
                    copy(bookingPeriod = msg.bookingPeriod, typeOfEnd = msg.typeEndPeriodBooking)

                is Msg.ChangeDateOfEndPeriod -> copy(dateOfEndPeriod = msg.date)
                is Msg.ChangeTypeOfEnd -> copy(typeOfEnd = msg.type)
                is Msg.UpdateAllZones -> copy(currentWorkspaceZones = msg.zones, allZonesList = msg.zones)
                is Msg.UpdateErrorCreatingBooking -> copy(isErrorBookingCreation = msg.isError)
            }
        }
    }
}