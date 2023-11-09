package band.effective.office.elevator.ui.booking

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.ModalCalendar
import band.effective.office.elevator.components.ModalCalendarDateRange
import band.effective.office.elevator.components.TimePickerModal
import band.effective.office.elevator.domain.models.BookingPeriod
import band.effective.office.elevator.domain.models.TypeEndPeriodBooking
import band.effective.office.elevator.ui.booking.components.BookingMainContentScreen
import band.effective.office.elevator.ui.booking.components.modals.BookingRepeatCard
import band.effective.office.elevator.ui.booking.components.modals.BookingResult
import band.effective.office.elevator.ui.booking.models.WorkSpaceUI
import band.effective.office.elevator.ui.booking.store.BookingStore
import band.effective.office.elevator.ui.models.TypesList
import band.effective.office.elevator.utils.isScrollingDown
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookingScreen(bookingComponent: BookingComponent) {

    val state by bookingComponent.state.collectAsState()
    val slot by bookingComponent.slot.subscribeAsState()

    LaunchedEffect(bookingComponent) {
        bookingComponent.label.collect { label ->
            when (label) {
                BookingStore.Label.CloseBookAccept -> TODO()
                BookingStore.Label.CloseBookPeriod -> TODO()
                BookingStore.Label.CloseBookRepeat -> TODO()
                BookingStore.Label.CloseCalendarForDateOfEnd -> TODO()
                BookingStore.Label.CloseChooseZone -> TODO()
                BookingStore.Label.CloseFinishTimeModal -> TODO()
                BookingStore.Label.CloseStartTimeModal -> TODO()
                is BookingStore.Label.OpenBookAccept -> bookingComponent.openSheet(BookingComponent.SheetConfig.BookAccept)
                BookingStore.Label.OpenBookPeriod -> bookingComponent.openSheet(BookingComponent.SheetConfig.BookPeriod)
                BookingStore.Label.OpenBookRepeat -> bookingComponent.openSheet(BookingComponent.SheetConfig.BookPeriod)
                BookingStore.Label.OpenCalendarForDateOfEnd -> TODO()
                BookingStore.Label.OpenChooseZone -> bookingComponent.openSheet(BookingComponent.SheetConfig.ChooseZone)
                BookingStore.Label.OpenFinishTimeModal -> TODO()
                BookingStore.Label.OpenStartTimeModal -> TODO()
                is BookingStore.Label.ShowToast -> TODO()
            }
        }
    }

    var timeTitle by remember { mutableStateOf(MainRes.strings.take_from) }
    timeTitle = if (state.isStart) {
        MainRes.strings.take_from
    } else {
        MainRes.strings.take_before
    }
    val sheetState =
        rememberModalBottomSheetState(if (slot.child == null) ModalBottomSheetValue.Hidden else ModalBottomSheetValue.Expanded)
    if (sheetState.targetValue == ModalBottomSheetValue.Hidden) {
        bookingComponent.closeSheet()
    }
    ModalBottomSheetLayout(
        sheetContent = { slot.child?.instance?.SheetContent() },
        sheetState = sheetState
    ) {
        Box() {
            slot.child?.instance?.content()
            Modals(
                showRepeatDialog = state.showRepeatDialog,
                showCalendar = state.showCalendar,
                showConfirm = state.showConfirm,
                showTimePicker = state.showTimePicker,
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
                onClickMainScreen = { bookingComponent.onOutput(BookingComponent.Output.OpenMainTab) },
                onClickApplyDate = { dates: List<LocalDate> ->
                    bookingComponent.onEvent(
                        BookingStore.Intent.ApplyDate(
                            date = dates
                        )
                    )
                },
                onClickCloseRepeatDialog = { bookingComponent.onEvent(BookingStore.Intent.CloseRepeatDialog) },
                isLoadingBookingCreation = state.isLoadingBookingCreation,
                dateOfEndPeriod = state.dateOfEndPeriod,
                showCalendarForEndDate = state.showCalendarForEndDate,
                onClickApplyDateOfEndPeriod = {
                    bookingComponent.onEvent(
                        BookingStore.Intent.SelectNewDateOfEnd(
                            it
                        )
                    )
                },
                onClickCloseCalendarForDateOfEnd = { bookingComponent.onEvent(BookingStore.Intent.CloseCalendarForEndDate) },
                startTime = state.selectedStartTime,
                endTime = state.selectedFinishTime,
                isErrorCreatingBooking = state.isErrorBookingCreation,
                bookingPeriod = state.bookingPeriod,
                isStart = state.isStart,
                timeTitle = timeTitle
            )
            BookingScreenContent(
                workSpaces = state.workSpaces,
                onClickOpenChoseZone = { bookingComponent.onEvent(BookingStore.Intent.OpenChooseZone) },
                onClickOpenBookPeriod = { bookingComponent.onEvent(BookingStore.Intent.OpenBookPeriod) },
                onClickOpenBookAccept = { workSpacesUI ->
                    bookingComponent.onEvent(BookingStore.Intent.OpenBookAccept(value = workSpacesUI))
                },
                startDate = state.selectedStartDate,
                finishDate = state.selectedFinishDate,
                repeatBookings = state.repeatBooking,
                onClickChangeSelectedType = {
                    bookingComponent.onEvent(
                        BookingStore.Intent.ChangeSelectedType(selectedType = it)
                    )
                },
                selectedTypesList = state.selectedType,
                isLoadingWorkspacesList = state.isLoadingListWorkspaces,
                typeOfTypeEndPeriodBooking = state.typeOfEnd,
                bookingPeriod = state.bookingPeriod,

                )
        }
    }
}

@Composable
private fun BookingScreenContent(
    isLoadingWorkspacesList: Boolean,
    workSpaces: List<WorkSpaceUI>,
    onClickOpenBookPeriod: () -> Unit,
    onClickOpenChoseZone: () -> Unit,
    onClickOpenBookAccept: (WorkSpaceUI) -> Unit,
    startDate: LocalDate,
    finishDate: LocalDate,
    repeatBookings: StringResource,
    onClickChangeSelectedType: (TypesList) -> Unit,
    selectedTypesList: TypesList,
    bookingPeriod: BookingPeriod,
    typeOfTypeEndPeriodBooking: TypeEndPeriodBooking
) {
    val scrollState = rememberLazyListState()
    val scrollIsDown = scrollState.isScrollingDown()


    var isExpandedCard by rememberSaveable { mutableStateOf(true) }
    val iconRotationStateCard by animateFloatAsState(targetValue = if (isExpandedCard) 90F else 270F)

    var isExpandedOptions by rememberSaveable { mutableStateOf(true) }
    val iconRotationStateOptions by animateFloatAsState(targetValue = if (isExpandedOptions) 90F else 270F)


    LaunchedEffect(scrollState.isScrollInProgress) {
        if (scrollIsDown) {
            isExpandedOptions = false
        }
    }
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
        bookingPeriod = bookingPeriod,
        typeEndPeriodBooking = typeOfTypeEndPeriodBooking,
        repeatBooking = repeatBookings,
        onClickChangeSelectedType = onClickChangeSelectedType,
        selectedTypesList = selectedTypesList,
        isLoadingWorkspacesList = isLoadingWorkspacesList
    )
}

@Composable
fun BoxScope.Modals(
    showRepeatDialog: Boolean,
    bookingPeriod: BookingPeriod,
    onClickOpenBookRepeat: (Pair<String, BookingPeriod>) -> Unit,
    onClickCloseRepeatDialog: () -> Unit,
    showCalendar: Boolean,
    currentDate: LocalDate,
    onClickApplyDate: (List<LocalDate>) -> Unit,
    onClickCloseCalendar: () -> Unit,
    showCalendarForEndDate: Boolean,
    dateOfEndPeriod: LocalDate,
    onClickApplyDateOfEndPeriod: (LocalDate?) -> Unit,
    onClickCloseCalendarForDateOfEnd: () -> Unit,
    showTimePicker: Boolean,
    isStart: Boolean,
    startTime: LocalTime,
    endTime: LocalTime,
    timeTitle: StringResource,
    onClickCloseTimeModal: () -> Unit,
    onClickSelectTime: (LocalTime) -> Unit,
    showConfirm: Boolean,
    onClickMainScreen: () -> Unit,
    onClickCloseBookingConfirm: () -> Unit,
    isLoadingBookingCreation: Boolean,
    isErrorCreatingBooking: Boolean
) {
    if (showRepeatDialog) {
        Dialog(
            content = {
                BookingRepeatCard(
                    onSelected = onClickOpenBookRepeat,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .align(Alignment.Center),
                    weekDays = if (bookingPeriod is BookingPeriod.Week)
                        bookingPeriod.selectedDayOfWeek
                    else emptyList()
                )
            },
            onDismissRequest = onClickCloseRepeatDialog,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        )
    }
    if (showCalendar) {
        Dialog(
            content = {
                ModalCalendarDateRange(
                    currentDate = currentDate,
                    onClickOk = onClickApplyDate,
                    onClickCansel = onClickCloseCalendar,
                    modifier = Modifier.padding(horizontal = 16.dp).align(Alignment.Center)
                )
            },
            properties = DialogProperties(usePlatformDefaultWidth = false),
            onDismissRequest = onClickCloseCalendar,
        )
    }
    if (showCalendarForEndDate) {
        Dialog(
            content = {
                ModalCalendar(
                    currentDate = dateOfEndPeriod,
                    onClickOk = onClickApplyDateOfEndPeriod,
                    onClickCansel = onClickCloseCalendarForDateOfEnd,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .align(Alignment.Center)
                )
            },
            properties = DialogProperties(usePlatformDefaultWidth = false),
            onDismissRequest = onClickCloseCalendarForDateOfEnd,
        )
    }

    if (showTimePicker) {
        Dialog(
            content = {
                TimePickerModal(
                    startTime = if (isStart) startTime else endTime,
                    titleText = stringResource(timeTitle),
                    modifier = Modifier.padding(horizontal = 16.dp)
                        .clip(shape = RoundedCornerShape(16.dp)).background(Color.White)
                        .align(Alignment.Center),
                    onClickCansel = onClickCloseTimeModal,
                    onClickOk = onClickSelectTime
                )
            },
            properties = DialogProperties(usePlatformDefaultWidth = false),
            onDismissRequest = onClickCloseTimeModal,
        )
    }

    if (showConfirm) {
        Dialog(
            content = {
                BookingResult(
                    onMain = onClickMainScreen,
                    close = onClickCloseBookingConfirm,
                    modifier = Modifier.padding(horizontal = 16.dp).align(Alignment.Center),
                    isLoading = isLoadingBookingCreation,
                    isError = isErrorCreatingBooking
                )
            },
            properties = DialogProperties(usePlatformDefaultWidth = false),
            onDismissRequest = onClickCloseBookingConfirm,
        )
    }
}