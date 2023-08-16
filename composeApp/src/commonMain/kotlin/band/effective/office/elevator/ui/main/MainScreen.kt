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
import band.effective.office.elevator.ui.main.components.BookingInformation
import band.effective.office.elevator.ui.main.components.BottomDialog
import band.effective.office.elevator.ui.main.components.ElevatorUIComponent
import band.effective.office.elevator.ui.main.store.MainStore
import band.effective.office.elevator.ui.models.ReservedSeat
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import effective.office.modalcustomdialog.Dialog
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(component: MainComponent) {

    val state by component.state.collectAsState()
    var isErrorMessageVisible by remember { mutableStateOf(false) }
    var isSuccessMessageVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf(MainRes.strings.something_went_wrong) }
    var showModalCalendar by remember { mutableStateOf(false) }
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

                MainStore.Label.ShowOptions -> {}
                MainStore.Label.OpenCalendar -> showModalCalendar = true
                MainStore.Label.CloseCalendar -> showModalCalendar = false
                MainStore.Label.OpenFiltersBottomDialog -> bottomSheetState.show()
                MainStore.Label.CloseFiltersBottomDialog -> bottomSheetState.hide()
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
            onClickBook = { component.onOutput(MainComponent.Output.OpenBookingScreen) },
            onClickShowOptions = { component.onEvent(MainStore.Intent.OnClickShowOption) },
            onClickOptionMenu = { index ->
                when (index){
                    0 -> component.onOutput(MainComponent.Output.OpenMap)
                    1 -> component.onOutput(MainComponent.Output.ExtendBooking)
                    2 -> component.onOutput(MainComponent.Output.RepeatBooking)
                    3 -> component.onOutput(MainComponent.Output.DeleteBooking)
                }
            },
            onClickOpenCalendar = { component.onEvent(MainStore.Intent.OnClickOpenCalendar) },
            onClickOpenBottomDialog = { component.onEvent(MainStore.Intent.OpenFiltersBottomDialog) },
            onClickCloseBottomDialog = { component.onEvent(MainStore.Intent.CloseFiltersBottomDialog)}
        )
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
    onClickBook: () -> Unit,
    onClickOptionMenu: (Int) -> Unit,
    onClickShowOptions: () -> Unit,
    onClickOpenCalendar: () -> Unit,
    onClickOpenBottomDialog: () -> Unit,
    onClickCloseBottomDialog: () -> Unit
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
                    onClickBook = onClickBook,
                    onClickOptionMenu = onClickOptionMenu,
                    onClickShowOptions = onClickShowOptions,
                    onClickOpenCalendar = onClickOpenCalendar,
                    onClickOpenBottomDialog = onClickOpenBottomDialog
                )
            }
        }
    }
}

