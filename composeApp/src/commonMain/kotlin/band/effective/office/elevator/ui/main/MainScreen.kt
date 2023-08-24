package band.effective.office.elevator.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.ModalCalendar
import band.effective.office.elevator.components.TitlePage
import band.effective.office.elevator.ui.booking.components.modals.DeleteBooking
import band.effective.office.elevator.successGreen
import band.effective.office.elevator.ui.booking.components.modals.BookingContextMenu
import band.effective.office.elevator.ui.employee.aboutEmployee.models.BookingsFilter
import band.effective.office.elevator.ui.main.components.BookingInformation
import band.effective.office.elevator.ui.main.components.BottomDialog
import band.effective.office.elevator.ui.main.components.ElevatorUIComponent
import band.effective.office.elevator.ui.main.store.MainStore
import band.effective.office.elevator.ui.models.ReservedSeat
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import effective.office.modalcustomdialog.Dialog
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDate


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(component: MainComponent) {

    val state by component.state.collectAsState()
    var isErrorMessageVisible by remember { mutableStateOf(false) }
    var isSuccessMessageVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf(MainRes.strings.something_went_wrong) }
    var selectSeat by remember { mutableStateOf(ReservedSeat.defaultSeat) }
    var showModalCalendar by remember { mutableStateOf(false) }
    var showOptionsMenu by remember { mutableStateOf(false) }
    var showDeleteBooking by remember { mutableStateOf(false) }
    var showModalOptionCard by remember { mutableStateOf(false) }

    var bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)


    LaunchedEffect(component) {
        component.label.collect { label ->
            when (label) {
                is MainStore.Label.ShowError -> {
                    errorMessage = (label.errorState.message
                        ?: MainRes.strings.something_went_wrong) as StringResource
                    isErrorMessageVisible = true
                    delay(3000)
                    isErrorMessageVisible = false
                }

                MainStore.Label.ShowSuccess -> {
                    isSuccessMessageVisible = true
                    delay(3000)
                    isSuccessMessageVisible = false
                }

                MainStore.Label.ShowOptions -> showOptionsMenu = true
                MainStore.Label.HideOptions -> showOptionsMenu = false

                MainStore.Label.OpenCalendar -> showModalCalendar = true
                MainStore.Label.CloseCalendar -> showModalCalendar = false
                MainStore.Label.OpenFiltersBottomDialog -> bottomSheetState.show()
                MainStore.Label.CloseFiltersBottomDialog -> bottomSheetState.hide()
                is MainStore.Label.OnClickOpenDeleteBooking -> {
                    selectSeat = label.seat
                    showDeleteBooking = true
                }
                MainStore.Label.OnClickCloseDeleteBooking -> showDeleteBooking = false
                MainStore.Label.OnClickOpenEditBooking -> {}
                MainStore.Label.OnClickCloseEditBooking -> {}
                MainStore.Label.OpenBooking -> {
                    component.onOutput(MainComponent.Output.OpenMap)
                }

                is MainStore.Label.DeleteBooking -> {
                    component.onOutput(MainComponent.Output.DeleteBooking(label.id))
                }

                MainStore.Label.CloseOption -> showModalOptionCard = false
            }
        }
    }

    Box(
        modifier = Modifier
            .background(ExtendedThemeColors.colors.whiteColor)
            .fillMaxSize()
    ) {
        MainScreenContent(
            reservedSeats = state.reservedSeats,
            bottomSheetState = bottomSheetState,
            currentDate = state.currentDate,
            dateFiltrationOnReserves = state.dateFiltrationOnReserves,
            onClickBook = { component.onOutput(MainComponent.Output.OpenBookingScreen) },
            onClickOptionMenu = { id ->
                component.onEvent(MainStore.Intent.OnClickShowOption(bookingId = id))
            },
            onClickOpenCalendar = { component.onEvent(MainStore.Intent.OnClickOpenCalendar) },
            onClickOpenBottomDialog = { component.onEvent(MainStore.Intent.OpenFiltersBottomDialog) },
            onClickCloseBottomDialog = {
                component.onEvent(
                    MainStore.Intent.CloseFiltersBottomDialog(
                        it
                    )
                )
            }
        )
//        Dialog(
//            content = {
//                DeleteBooking(
//                    place = selectSeat.seatName,
//                    fullDate = selectSeat.bookingDay + " "+ selectSeat.bookingTime,
//                    onCanselCLick = {component.onEvent(MainStore.Intent.OnClickCloseDeleteBooking)},
//                    onDeleteClick = {component.onEvent(MainStore.Intent.OnClickDeleteBooking(selectSeat))}
//                )
//            },
//            onDismissRequest = {component.onEvent(MainStore.Intent.OnClickCloseDeleteBooking)},
//            showDialog = showDeleteBooking,
//            modifier = Modifier.align(Alignment.Center).padding(horizontal = 16.dp)
//        )
        Dialog(
            content = {
                ModalCalendar(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    onClickCansel = { component.onEvent(MainStore.Intent.OnClickCloseCalendar) },
                    onClickOk = { component.onEvent(MainStore.Intent.OnClickApplyDate(it)) },
                    currentDate = state.currentDate
                )
            },
            onDismissRequest = { component.onEvent(MainStore.Intent.OnClickCloseCalendar) },
            showDialog = showModalCalendar,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        Dialog(
            content = {
                BookingContextMenu(
                    onClick = {},
                    onClickOpenDeleteBooking = { component.onEvent(MainStore.Intent.OnClickDeleteBooking) },
                    onClickBook = { component.onOutput(MainComponent.Output.OpenMap) }
                )
            },
            onDismissRequest = { component.onEvent(MainStore.Intent.OnClickCloseCalendar) },
            showDialog = showModalCalendar,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        SnackBarErrorMessage(
            modifier = Modifier.align(Alignment.BottomCenter),
            isVisible = isErrorMessageVisible,
            message = stringResource(errorMessage)
        )
        SnackBarSuccessMessage(
            modifier = Modifier.align(Alignment.BottomCenter),
            isVisible = isSuccessMessageVisible
        )
    }
}

@Composable
private fun SnackBarErrorMessage(modifier: Modifier, isVisible: Boolean, message: String) {
    AnimatedVisibility(modifier = modifier, visible = isVisible) {
        Snackbar(modifier.padding(16.dp), backgroundColor = MaterialTheme.colors.error) {
            Text(text = message)
        }
    }
}

@Composable
private fun SnackBarSuccessMessage(modifier: Modifier, isVisible: Boolean) {
    /*AnimatedVisibility(modifier = modifier, visible = isVisible) {
        Snackbar(
            modifier.padding(16.dp),
            backgroundColor = successGreen
        ) {
            Text(text = stringResource(MainRes.strings.elevator_called_successfully))
        }
    }

     */
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    bottomSheetState: ModalBottomSheetState,
    reservedSeats: List<ReservedSeat>,
    currentDate: LocalDate,
    dateFiltrationOnReserves: Boolean,
    onClickBook: () -> Unit,
    onClickOptionMenu: (String) -> Unit,
    onClickOpenCalendar: () -> Unit,
    onClickOpenBottomDialog: () -> Unit,
    onClickCloseBottomDialog: (BookingsFilter) -> Unit,
) {
    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetState = bottomSheetState,
        sheetContent = {
            BottomDialog(
                Modifier,
                stringResource(MainRes.strings.filter_by_category),
                onClickCloseBottomDialog
            )
        }
        //,sheetPeekHeight = 0.dp
    ) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                TitlePage(
                    title = stringResource(MainRes.strings.main),
                    modifier = Modifier.padding(top = 60.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                ElevatorUIComponent(
                    onClickCallElevator = {}
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colors.onBackground)
                    .padding(horizontal = 16.dp),
            ) {
                BookingInformation(
                    reservedSeats = reservedSeats,
                    currentDate = currentDate,
                    dateFiltrationOnReserves = dateFiltrationOnReserves,
                    onClickBook = onClickBook,
                    onClickOptionMenu = onClickOptionMenu,
                    onClickOpenCalendar = onClickOpenCalendar,
                    onClickOpenBottomDialog = onClickOpenBottomDialog,
                )
            }
        }
    }
}

