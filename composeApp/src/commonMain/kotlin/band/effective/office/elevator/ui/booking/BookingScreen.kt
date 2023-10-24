package band.effective.office.elevator.ui.booking

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.ModalCalendar
import band.effective.office.elevator.components.ModalCalendarDateRange
import band.effective.office.elevator.components.MultiBottomSheet
import band.effective.office.elevator.components.TimePickerModal
import band.effective.office.elevator.components.bottomSheet.BottomSheetItem
import band.effective.office.elevator.components.bottomSheet.MultiBottomSheetController
import band.effective.office.elevator.components.bottomSheet.rememberMultiBottomSheetController
import band.effective.office.elevator.domain.models.BookingInfo
import band.effective.office.elevator.domain.models.BookingPeriod
import band.effective.office.elevator.expects.showToast
import band.effective.office.elevator.ui.booking.components.BookingMainContentScreen
import band.effective.office.elevator.ui.booking.components.modals.BookAccept
import band.effective.office.elevator.ui.booking.components.modals.BookingPeriod
import band.effective.office.elevator.ui.booking.components.modals.BookingRepeat
import band.effective.office.elevator.ui.booking.components.modals.BookingRepeatCard
import band.effective.office.elevator.ui.booking.components.modals.BookingSuccess
import band.effective.office.elevator.ui.booking.components.modals.ChooseZone
import band.effective.office.elevator.ui.booking.models.BottomSheetNames
import band.effective.office.elevator.ui.booking.models.Frequency
import band.effective.office.elevator.ui.booking.models.WorkSpaceType
import band.effective.office.elevator.ui.booking.models.WorkSpaceUI
import band.effective.office.elevator.ui.booking.store.BookingStore
import band.effective.office.elevator.ui.models.TypesList
import band.effective.office.elevator.utils.Stack
import band.effective.office.elevator.utils.isScrollingDown
import band.effective.office.elevator.utils.stackOf
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import effective.office.modalcustomdialog.Dialog
import io.github.aakira.napier.Napier
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.atTime

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookingScreen(bookingComponent: BookingComponent) {

    val state by bookingComponent.state.collectAsState()

    val showChooseZone = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { sheetState ->
            when(sheetState) {
                ModalBottomSheetValue.Expanded -> true
                ModalBottomSheetValue.Hidden -> {
                    bookingComponent.onEvent(BookingStore.Intent.CloseChooseZone)
                    true
                }

                ModalBottomSheetValue.HalfExpanded -> true
            }
        }
    )
    val showBookPeriod = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { sheetState ->
            when (sheetState) {
                ModalBottomSheetValue.Expanded -> true
                ModalBottomSheetValue.Hidden -> {
                    bookingComponent.onEvent(BookingStore.Intent.CloseBookPeriod)
                    true
                }
                ModalBottomSheetValue.HalfExpanded ->  true
            }
        }
    )
    val showBookAccept = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { sheetState ->
            when (sheetState) {
                ModalBottomSheetValue.Expanded -> {
                    true
                }
                ModalBottomSheetValue.Hidden -> {
                    bookingComponent.onEvent(BookingStore.Intent.CloseBookAccept)
                    true
                }
                ModalBottomSheetValue.HalfExpanded -> {
                    true
                }
            }
        }
    )
    val showBookRepeat = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        confirmValueChange = { sheetState ->
            when (sheetState) {
                ModalBottomSheetValue.Expanded -> true
                ModalBottomSheetValue.Hidden -> {
                    bookingComponent.onEvent(BookingStore.Intent.CloseBookRepeat)
                    true
                }
                ModalBottomSheetValue.HalfExpanded -> true
            }
        }
    )

    val stackRemember: Stack<String> by remember { mutableStateOf(stackOf()) }

    val multiBottomSheetController = rememberMultiBottomSheetController(
        sheetStack = stackRemember,
        sheetContents = mapOf(
            BottomSheetNames.CHOOSE_ZONE.name to BottomSheetItem(
                bottomSheetContentState = showChooseZone
            ) {
                ChooseZone(
                    sheetTile = stringResource(
                        if (state.workSpacesType == WorkSpaceType.WORK_PLACE) MainRes.strings.selection_zones
                        else MainRes.strings.selection_rooms
                    ),
                    workSpacecZone = state.currentWorkspaceZones,
                    onClickCloseChoseZone = { bookingComponent.onEvent(BookingStore.Intent.CloseChooseZone) },
                    onClickConfirmSelectedZone = {
                        bookingComponent.onEvent(
                            BookingStore.Intent.ChangeSelectedWorkSpacesZone(it)
                        )
                    }
                )
            },
            BottomSheetNames.BOOK_ACCEPT.name to BottomSheetItem(
                bottomSheetContentState = showBookAccept
            ) {
                BookAccept(
                    onClickCloseBookAccept = { bookingComponent.onEvent(BookingStore.Intent.CloseBookAccept) },
                    confirmBooking = { bookingComponent.onEvent(BookingStore.Intent.OpenConfirmBooking) },
                    bookingInfo = BookingInfo(
                        id = state.bookingInfo.id,
                        ownerId = "",
                        workSpaceId = "",
                        seatName = state.bookingInfo.seatName,
                        dateOfStart = state.selectedStartDate.atTime(state.selectedStartTime),
                        dateOfEnd = state.selectedFinishDate.atTime(state.selectedFinishTime)
                    ),
                    frequency = state.frequency,
                    period = state.bookingPeriod
                )
            },
            BottomSheetNames.BOOK_PERIOD.name to BottomSheetItem(
                bottomSheetContentState = showBookPeriod
            ) {
                BookingPeriod(
                    startDate = state.selectedStartDate,
                    startTime = state.selectedStartTime,
                    finishTime = state.selectedFinishTime,
                    repeatBooking = stringResource(state.repeatBooking),
                    switchChecked = state.wholeDay,
                    closeClick = { bookingComponent.onEvent(BookingStore.Intent.CloseBookPeriod) },
                    onSelectAllDay = {
                        bookingComponent.onEvent(
                            BookingStore.Intent.ChangeWholeDay(
                                wholeDay = state.wholeDay
                            )
                        )
                    },
                    bookDates = {
                        bookingComponent.onEvent(
                            BookingStore.Intent.OpenCalendar
                        )
                    },
                    bookStartTime = {
                        bookingComponent.onEvent(
                            BookingStore.Intent.OpenStartTimeModal(
                                isStart = true,
                                time = state.selectedStartTime
                            )
                        )
                    },
                    bookFinishTime = {
                        bookingComponent.onEvent(
                            BookingStore.Intent.OpenStartTimeModal(
                                isStart = false,
                                time = state.selectedFinishTime
                            )
                        )
                    },
                    bookingRepeat = {
                        bookingComponent.onEvent(BookingStore.Intent.OpenRepeatDialog)
                    },
                    onClickSearchSuitableOptions = { bookingComponent.onEvent(BookingStore.Intent.SearchSuitableOptions) },
                    frequency = state.frequency,
                    finishDate = state.selectedFinishDate,
                )
            },
            BottomSheetNames.BOOK_REPEAT.name to BottomSheetItem(
                bottomSheetContentState = showBookRepeat
            ) {
                BookingRepeat(
                    backButtonClicked = { bookingComponent.onEvent(BookingStore.Intent.CloseBookRepeat) },
                    confirmBooking = { frequency ->
                        bookingComponent.onEvent(BookingStore.Intent.ChangeFrequency(frequency = frequency))
                    },
                    onSelected = {},
                    onClickOpenCalendar = {bookingComponent.onEvent(BookingStore.Intent.OpenCalendarForEndDate)},
                    selectedDayOfEnd = state.dateOfEndPeriod
                )
            },
        )
    )

    var showRepeatDialog by remember { mutableStateOf(false) }
    var showCalendar by remember { mutableStateOf(false) }
    var showConfirm by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showCalendarForEndDate by remember { mutableStateOf(false) }

    LaunchedEffect(bookingComponent) {
        bookingComponent.label.collect { label ->
            when (label) {
                is BookingStore.Label.OpenChooseZone -> multiBottomSheetController.showSheet(
                    BottomSheetNames.CHOOSE_ZONE.name
                )

                is BookingStore.Label.CloseChooseZone -> multiBottomSheetController.closeCurrentSheet()
                is BookingStore.Label.OpenBookPeriod -> multiBottomSheetController.showSheet(
                    BottomSheetNames.BOOK_PERIOD.name
                )

                is BookingStore.Label.CloseBookPeriod -> multiBottomSheetController.closeCurrentSheet()
                is BookingStore.Label.OpenRepeatDialog -> showRepeatDialog = true
                is BookingStore.Label.CloseRepeatDialog -> showRepeatDialog = false
                is BookingStore.Label.OpenBookAccept -> {
                    multiBottomSheetController.showSheet(
                        BottomSheetNames.BOOK_ACCEPT.name
                    )
                }

                is BookingStore.Label.CloseBookAccept -> multiBottomSheetController.closeCurrentSheet()
                is BookingStore.Label.OpenCalendar -> showCalendar = true
                is BookingStore.Label.CloseCalendar -> showCalendar = false
                is BookingStore.Label.OpenConfirmBooking -> showConfirm = true
                is BookingStore.Label.CloseConfirmBooking -> showConfirm = false
                is BookingStore.Label.OpenStartTimeModal -> showTimePicker = true
                is BookingStore.Label.CloseStartTimeModal -> showTimePicker = false
                is BookingStore.Label.OpenBookRepeat -> multiBottomSheetController.showSheet(
                    BottomSheetNames.BOOK_REPEAT.name
                )

                is BookingStore.Label.CloseBookRepeat -> {
                    Napier.d { "Close book repeat label" }
                    multiBottomSheetController.closeCurrentSheet()
                }
                BookingStore.Label.OpenFinishTimeModal -> showTimePicker = true
                BookingStore.Label.CloseFinishTimeModal -> showTimePicker = false
                is BookingStore.Label.ShowToast -> showToast(label.message)
                BookingStore.Label.CloseCalendarForDateOfEnd -> showCalendarForEndDate = false
                BookingStore.Label.OpenCalendarForDateOfEnd -> showCalendarForEndDate = true
            }
        }
    }

    BookingScreenContent(
        workSpaces = state.workSpaces,
        multiBottomSheetController = multiBottomSheetController,
        showRepeatDialog = showRepeatDialog,
        showCalendar = showCalendar,
        showConfirm = showConfirm,
        showTimePicker = showTimePicker,
        currentDate = state.currentDate,
        onClickOpenBookRepeat = { pair ->
            bookingComponent.onEvent(BookingStore.Intent.OnSelectBookingPeriod(pair = pair))
        },
        onClickCloseTimeModal = { bookingComponent.onEvent(BookingStore.Intent.CloseStartTimeModal) },
        onClickSelectTime = { time: LocalTime ->
            bookingComponent.onEvent(
                BookingStore.Intent.ApplyTime(
                    time = time,
                    isStart = state.isStart
                )
            )
        },
        onClickCloseBookingConfirm = { bookingComponent.onEvent(BookingStore.Intent.CloseConfirmBooking) },
        onClickCloseCalendar = { bookingComponent.onEvent(BookingStore.Intent.CloseCalendar) },
        onClickOpenChoseZone = { bookingComponent.onEvent(BookingStore.Intent.OpenChooseZone) },
        onClickOpenBookPeriod = { bookingComponent.onEvent(BookingStore.Intent.OpenBookPeriod) },
        onClickMainScreen = { bookingComponent.onOutput(BookingComponent.Output.OpenMainTab) },
        onClickOpenBookAccept = { workSpacesUI ->
            bookingComponent.onEvent(BookingStore.Intent.OpenBookAccept(value = workSpacesUI))
        },
        onClickApplyDate = { dates: List<LocalDate> ->
            bookingComponent.onEvent(
                BookingStore.Intent.ApplyDate(
                    date = dates
                )
            )
        },
        isStart = state.isStart,
        startDate = state.selectedStartDate,
        finishDate = state.selectedFinishDate,
        frequency = state.frequency,
        repeatBookings = state.repeatBooking,
        onClickChangeSelectedType = {
            bookingComponent.onEvent(
                BookingStore.Intent.ChangeSelectedType(selectedType = it)
            )
        },
        selectedTypesList = state.selectedType,
        onClickCloseRepeatDialog = {bookingComponent.onEvent(BookingStore.Intent.CloseRepeatDialog)},
        isLoadingWorkspacesList = state.isLoadingListWorkspaces,
        isLoadingBookingCreation = state.isLoadingBookingCreation,
        dateOfEndPeriod = state.dateOfEndPeriod,
        showCalendarForEndDate = showCalendarForEndDate,
        onClickApplyDateOfEndPeriod = {bookingComponent.onEvent(BookingStore.Intent.SelectNewDateOfEnd(it))},
        onClickCloseCalendarForDateOfEnd = {bookingComponent.onEvent(BookingStore.Intent.CloseCalendarForEndDate)}
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BookingScreenContent(
    isLoadingWorkspacesList: Boolean,
    workSpaces: List<WorkSpaceUI>,
    multiBottomSheetController: MultiBottomSheetController,
    onClickOpenBookPeriod: () -> Unit,
    onClickOpenChoseZone: () -> Unit,
    showRepeatDialog: Boolean,
    onClickOpenBookAccept: (WorkSpaceUI) -> Unit,
    onClickCloseCalendar: () -> Unit,
    showCalendar: Boolean,
    showCalendarForEndDate: Boolean,
    onClickApplyDateOfEndPeriod: (LocalDate?) -> Unit,
    onClickCloseCalendarForDateOfEnd: () -> Unit,
    onClickApplyDate: (List<LocalDate>) -> Unit,
    currentDate: LocalDate,
    onClickCloseBookingConfirm: () -> Unit,
    showConfirm: Boolean,
    onClickMainScreen: () -> Unit,
    showTimePicker: Boolean,
    onClickCloseTimeModal: () -> Unit,
    onClickSelectTime: (LocalTime) -> Unit,
    onClickOpenBookRepeat: (Pair<String, BookingPeriod>) -> Unit,
    isStart: Boolean,
    startDate: LocalDate,
    finishDate: LocalDate,
    frequency: Frequency,
    repeatBookings: StringResource,
    onClickChangeSelectedType: (TypesList) -> Unit,
    selectedTypesList: TypesList,
    onClickCloseRepeatDialog: () -> Unit,
    isLoadingBookingCreation: Boolean,
    dateOfEndPeriod: LocalDate
) {
    val scrollState = rememberLazyListState()
    val scrollIsDown = scrollState.isScrollingDown()

    var timeTitle by remember { mutableStateOf(MainRes.strings.take_from) }

    var isExpandedCard by rememberSaveable { mutableStateOf(true) }
    val iconRotationStateCard by animateFloatAsState(targetValue = if (isExpandedCard) 90F else 270F)

    var isExpandedOptions by rememberSaveable { mutableStateOf(true) }
    val iconRotationStateOptions by animateFloatAsState(targetValue = if (isExpandedOptions) 90F else 270F)


    LaunchedEffect(scrollState.isScrollInProgress) {
        if (scrollIsDown) {
            isExpandedOptions = false
        }
    }

    timeTitle = if (isStart){
        MainRes.strings.take_from
    }else{
        MainRes.strings.take_before
    }
    Box {
        MultiBottomSheet(
            multiBottomSheetController = multiBottomSheetController
        ) {
            BookingMainContentScreen(
                workSpaces = workSpaces,
                scrollState = scrollState,
                isExpandedCard = isExpandedCard,
                isExpandedOptions = isExpandedOptions,
                iconRotationStateCard = iconRotationStateCard,
                iconRotationStateOptions = iconRotationStateOptions,
                onClickOpenBookAccept = onClickOpenBookAccept,
                onClickOpenBookPeriod = onClickOpenBookPeriod,
                onClickOpenChoseZone = onClickOpenChoseZone,
                onClickExpandedMap = { isExpandedCard = !isExpandedCard },
                onClickExpandedOption = { isExpandedOptions = !isExpandedOptions },
                startDate = startDate,
                finishDate = finishDate,
                frequency = frequency,
                repeatBooking=repeatBookings,
                onClickChangeSelectedType = onClickChangeSelectedType,
                selectedTypesList = selectedTypesList,
                isLoadingWorkspacesList = isLoadingWorkspacesList
            )
        }

        Dialog(
            content = {
                BookingRepeatCard(
                    onSelected = onClickOpenBookRepeat,
                    modifier = Modifier.padding(horizontal = 16.dp).align(Alignment.Center),
                    frequency = frequency
                )
            },
            onDismissRequest = onClickCloseRepeatDialog,
            showDialog = showRepeatDialog,
            modifier = Modifier.padding(horizontal = 16.dp).align(Alignment.Center)
        )

        Dialog(
            content = {
                ModalCalendarDateRange(
                    currentDate = currentDate,
                    onClickOk = onClickApplyDate,
                    onClickCansel = onClickCloseCalendar,
                    modifier = Modifier.padding(horizontal = 16.dp).align(Alignment.Center)
                )
            },
            onDismissRequest = onClickCloseCalendar,
            showDialog = showCalendar,
            modifier = Modifier.padding(horizontal = 16.dp).align(Alignment.Center)
        )
        Dialog(
            content = {
                ModalCalendar(
                    currentDate = dateOfEndPeriod,
                    onClickOk = onClickApplyDateOfEndPeriod,
                    onClickCansel = onClickCloseCalendarForDateOfEnd,
                    modifier = Modifier.padding(horizontal = 16.dp).align(Alignment.Center)
                )
            },
            onDismissRequest = onClickCloseCalendarForDateOfEnd,
            showDialog = showCalendarForEndDate,
            modifier = Modifier.padding(horizontal = 16.dp).align(Alignment.Center)
        )
        Dialog(
            content = {
                TimePickerModal(
                    titleText = stringResource(timeTitle),
                    modifier = Modifier.padding(horizontal = 16.dp)
                        .clip(shape = RoundedCornerShape(16.dp)).background(Color.White)
                        .align(Alignment.Center),
                    onClickCansel = onClickCloseTimeModal,
                    onClickOk = onClickSelectTime
                )
            },
            onDismissRequest = onClickCloseTimeModal,
            showDialog = showTimePicker,
            modifier = Modifier.padding(horizontal = 16.dp)
                .clip(shape = RoundedCornerShape(16.dp)).background(Color.White)
                .align(Alignment.Center)
        )

        Dialog(
            content = {
                BookingSuccess(
                    onMain = onClickMainScreen,
                    close = onClickCloseBookingConfirm,
                    modifier = Modifier.padding(horizontal = 16.dp).align(Alignment.Center),
                    isLoading = isLoadingBookingCreation
                )
            },
            onDismissRequest = onClickCloseBookingConfirm,
            showDialog = showConfirm,
            modifier = Modifier.padding(horizontal = 16.dp).align(Alignment.Center)
        )
    }
}





