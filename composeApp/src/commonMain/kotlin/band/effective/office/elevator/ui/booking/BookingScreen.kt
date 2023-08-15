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
import band.effective.office.elevator.components.MultiBottomSheet
import band.effective.office.elevator.components.TimePickerModal
import band.effective.office.elevator.components.bottomSheet.BottomSheetItem
import band.effective.office.elevator.components.bottomSheet.MultiBottomSheetController
import band.effective.office.elevator.components.bottomSheet.rememberMultiBottomSheetController
import band.effective.office.elevator.expects.showToast
import band.effective.office.elevator.ui.booking.components.BookingMainContentScreen
import band.effective.office.elevator.ui.booking.components.modals.BookAccept
import band.effective.office.elevator.ui.booking.components.modals.BookingPeriod
import band.effective.office.elevator.ui.booking.components.modals.BookingRepeat
import band.effective.office.elevator.ui.booking.components.modals.BookingRepeatCard
import band.effective.office.elevator.ui.booking.components.modals.BookingSuccess
import band.effective.office.elevator.ui.booking.components.modals.ChooseZone
import band.effective.office.elevator.ui.booking.models.BottomSheetNames
import band.effective.office.elevator.ui.booking.models.WorkSpaceType
import band.effective.office.elevator.ui.booking.models.WorkSpaceUI
import band.effective.office.elevator.ui.booking.models.WorkSpaceZone
import band.effective.office.elevator.ui.booking.store.BookingStore
import band.effective.office.elevator.utils.Stack
import band.effective.office.elevator.utils.isScrollingDown
import band.effective.office.elevator.utils.stackOf
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookingScreen(bookingComponent: BookingComponent) {

    val state by bookingComponent.state.collectAsState()

    val showChooseZone = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val showBookPeriod = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val showBookAccept = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val showBookRepeat = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val workSpacesUI = listOf(
        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = stringResource(MainRes.strings.cassiopeia_zone),
            workSpaceType = WorkSpaceType.MEETING_ROOM
        ),
        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = stringResource(MainRes.strings.arrakis_zone),
            workSpaceType = WorkSpaceType.MEETING_ROOM
        ),
        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = stringResource(MainRes.strings.mars_zone),
            workSpaceType = WorkSpaceType.MEETING_ROOM
        ),
        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = stringResource(MainRes.strings.antares_zone),
            workSpaceType = WorkSpaceType.MEETING_ROOM
        ),
        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = stringResource(MainRes.strings.sirius_zone),
            workSpaceType = WorkSpaceType.MEETING_ROOM
        ),

        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = stringResource(MainRes.strings.moon_room),
            workSpaceType = WorkSpaceType.WORK_PLACE
        ),
        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = stringResource(MainRes.strings.sun_room),
            workSpaceType = WorkSpaceType.WORK_PLACE
        ),
        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = stringResource(MainRes.strings.mercury_room),
            workSpaceType = WorkSpaceType.WORK_PLACE
        ),
        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = stringResource(MainRes.strings.pluto_room),
            workSpaceType = WorkSpaceType.WORK_PLACE
        )
    )

    val allBookingZone = listOf(
        WorkSpaceZone(name = stringResource(MainRes.strings.sirius_zone), isSelected = true),
        WorkSpaceZone(name = stringResource(MainRes.strings.antares_zone), isSelected = true),
        WorkSpaceZone(name = stringResource(MainRes.strings.mars_zone), isSelected = true),
        WorkSpaceZone(name = stringResource(MainRes.strings.cassiopeia_zone), isSelected = true),
        WorkSpaceZone(name = stringResource(MainRes.strings.arrakis_zone), isSelected = true),
    )

    val allMeetingRooms = listOf(
        WorkSpaceZone(name = stringResource(MainRes.strings.moon_room), isSelected = true),
        WorkSpaceZone(name = stringResource(MainRes.strings.sun_room), isSelected = true),
        WorkSpaceZone(name = stringResource(MainRes.strings.mercury_room), isSelected = true),
        WorkSpaceZone(name = stringResource(MainRes.strings.pluto_room), isSelected = true),
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
                    workSpacecZone = state.workSpacesZone,
                    onClickCloseChoseZone = { bookingComponent.onEvent(BookingStore.Intent.CloseChooseZone) },
                    onClickConfirmSelectedZone = {
                        bookingComponent.onEvent(
                            BookingStore.Intent.ChangeSelectedWorkSpacesZone(
                                it
                            )
                        )
                    }
                )
            },
            BottomSheetNames.BOOK_ACCEPT.name to BottomSheetItem(
                bottomSheetContentState = showBookAccept
            ) {
                BookAccept(
                    onClickCloseBookAccept = { bookingComponent.onEvent(BookingStore.Intent.CloseBookAccept) },
                    confirmBooking = { bookingComponent.onEvent(BookingStore.Intent.OpenConfirmBooking) }
                )
            },
            BottomSheetNames.BOOK_PERIOD.name to BottomSheetItem(
                bottomSheetContentState = showBookPeriod
            ) {
                BookingPeriod(
                    startDate = "Чт, 27 июл. 2023 г.",
                    startTime = "10:30",
                    finishTime = "15:30",
                    repeatBooking = "Бронирование не повторяется",
                    switchChecked = false,
                    closeClick = { bookingComponent.onEvent(BookingStore.Intent.CloseBookPeriod) },
                    onSelectAllDay = { false },
                    bookStartDate = { bookingComponent.onEvent(BookingStore.Intent.OpenCalendar) },
                    bookStartTime = { bookingComponent.onEvent(BookingStore.Intent.OpenTimeModal) },
                    bookFinishTime = { bookingComponent.onEvent(BookingStore.Intent.OpenTimeModal) },
                    bookingRepeat = { bookingComponent.onEvent(BookingStore.Intent.OpenRepeatDialog) },
                    onClickSearchSuitableOptions = { bookingComponent.onEvent(BookingStore.Intent.SearchSuitableOptions) }
                )
            },
            BottomSheetNames.BOOK_REPEAT.name to BottomSheetItem(
                bottomSheetContentState = showBookRepeat
            ) {
                BookingRepeat(
                    backButtonClicked = { bookingComponent.onEvent(BookingStore.Intent.CloseBookRepeat) },
                    dropDownClick = {},
                    confirmBooking = {},
                    onSelected = {},
                    onDaySelected = {}
                )
            },
        )
    )

    var showRepeatDialog by remember { mutableStateOf(false) }
    var showCalendar by remember { mutableStateOf(false) }
    var showConfirm by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

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
                is BookingStore.Label.OpenBookAccept -> multiBottomSheetController.showSheet(
                    BottomSheetNames.BOOK_ACCEPT.name
                )

                is BookingStore.Label.CloseBookAccept -> multiBottomSheetController.closeCurrentSheet()
                is BookingStore.Label.OpenCalendar -> showCalendar = true
                is BookingStore.Label.CloseCalendar -> showCalendar = false
                is BookingStore.Label.OpenConfirmBooking -> showConfirm = true
                is BookingStore.Label.CloseConfirmBooking -> showConfirm = false
                is BookingStore.Label.OpenTimeModal -> showTimePicker = true
                is BookingStore.Label.CloseTimeModal -> showTimePicker = false
                is BookingStore.Label.OpenBookRepeat -> multiBottomSheetController.showSheet(
                    BottomSheetNames.BOOK_REPEAT.name
                )

                is BookingStore.Label.CloseBookRepeat -> multiBottomSheetController.closeCurrentSheet()
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
        onClickOpenBookRepeat = { bookingComponent.onEvent(BookingStore.Intent.OpenBookRepeat) },
        onClickCloseTimeModal = { bookingComponent.onEvent(BookingStore.Intent.CloseTimeModal) },
        onClickSelectTime = { time: LocalTime ->
            bookingComponent.onEvent(
                BookingStore.Intent.ApplyTime(
                    time = time
                )
            )
        },
        onClickCloseBookingConfirm = { bookingComponent.onEvent(BookingStore.Intent.CloseConfirmBooking) },
        onClickCloseCalendar = { bookingComponent.onEvent(BookingStore.Intent.CloseCalendar) },
        onClickOpenChoseZone = { bookingComponent.onEvent(BookingStore.Intent.OpenChooseZone) },
        onClickOpenBookPeriod = { bookingComponent.onEvent(BookingStore.Intent.OpenBookPeriod) },
        onClickMainScreen = { bookingComponent.onOutput(BookingComponent.Output.OpenMainTab) },
        onClickOpenBookAccept = { bookingComponent.onEvent(BookingStore.Intent.OpenBookAccept) },
        onClickApplyDate = { date: LocalDate? ->
            bookingComponent.onEvent(
                BookingStore.Intent.ApplyDate(
                    date = date
                )
            )
        },
        onClickChangeZone = { type ->
            with(if (type == WorkSpaceType.MEETING_ROOM) allBookingZone else allMeetingRooms) {
                bookingComponent.onEvent(
                    BookingStore.Intent.ChangeSelectedWorkSpacesZone(
                        workSpaceZone = this@with
                    )
                )
                bookingComponent.onEvent(BookingStore.Intent.ChangeWorkSpacesUI(workSpaces = workSpacesUI.filter { workSpaceUI -> workSpaceUI.workSpaceType == type }))
            }
        }
    )
}

@Composable
private fun BookingScreenContent(
    workSpaces: List<WorkSpaceUI>,
    multiBottomSheetController: MultiBottomSheetController,
    onClickOpenBookPeriod: () -> Unit,
    onClickOpenChoseZone: () -> Unit,
    showRepeatDialog: Boolean,
    onClickOpenBookAccept: (String) -> Unit,
    onClickCloseCalendar: () -> Unit,
    showCalendar: Boolean,
    onClickApplyDate: (LocalDate?) -> Unit,
    currentDate: LocalDate,
    onClickCloseBookingConfirm: () -> Unit,
    showConfirm: Boolean,
    onClickMainScreen: () -> Unit,
    showTimePicker: Boolean,
    onClickCloseTimeModal: () -> Unit,
    onClickSelectTime: (LocalTime) -> Unit,
    onClickOpenBookRepeat: () -> Unit,
    onClickChangeZone: (WorkSpaceType) -> Unit
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
            isExpandedCard = false
            isExpandedOptions = false
        }
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
                onClickChangeZone = onClickChangeZone
            )
        }

        if (showRepeatDialog) {
            BookingRepeatCard(
                onSelected = onClickOpenBookRepeat,
                modifier = Modifier.padding(horizontal = 16.dp).align(Alignment.Center)
            )
        }
        if (showCalendar) {
            ModalCalendar(
                currentDate = currentDate,
                onClickOk = onClickApplyDate,
                onClickCansel = onClickCloseCalendar,
                modifier = Modifier.padding(horizontal = 16.dp).align(Alignment.Center)
            )
        }

        if (showTimePicker) {
            TimePickerModal(
                titleText = stringResource(timeTitle),
                modifier = Modifier.padding(horizontal = 16.dp)
                    .clip(shape = RoundedCornerShape(16.dp)).background(Color.White)
                    .align(Alignment.Center),
                onClickCansel = onClickCloseTimeModal,
                onClickOk = onClickSelectTime
            )
        }

        if (showConfirm) {
            BookingSuccess(
                onMain = onClickMainScreen,
                close = onClickCloseBookingConfirm,
                modifier = Modifier.padding(horizontal = 16.dp).align(Alignment.Center)
            )
        }
    }
}





