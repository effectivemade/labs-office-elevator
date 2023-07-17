package band.effective.office.elevator.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.ModalCalendar
import band.effective.office.elevator.components.TitlePage
import band.effective.office.elevator.successGreen
import band.effective.office.elevator.ui.main.components.BookingInformation
import band.effective.office.elevator.ui.main.components.ElevatorUIComponent
import band.effective.office.elevator.ui.main.store.MainStore
import band.effective.office.elevator.ui.models.ElevatorState
import band.effective.office.elevator.ui.models.ReservedSeat
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.delay

@Composable
fun MainScreen(component: MainComponent) {

    val state by component.state.collectAsState()
    var isErrorMessageVisible by remember { mutableStateOf(false) }
    var isSuccessMessageVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf(MainRes.strings.something_went_wrong) }
    var showModalCalendar by remember { mutableStateOf(false) }

    LaunchedEffect(component) {
        component.label.collect { label ->
            when (label) {
                is MainStore.Label.ShowError -> {
                    errorMessage = (label.errorState.message ?: MainRes.strings.something_went_wrong) as StringResource
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
            }
        }
    }

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        MainScreenContent(
            reservedSeats = state.reservedSeats,
            onClickBook = { component.onOutput(MainComponent.Output.OpenBookingScreen) },
            onClickShowOptions = { component.onEvent(MainStore.Intent.OnClickShowOption) },
            onClickShowMap = { component.onOutput(MainComponent.Output.OpenMap) },
            onClickOpenCalendar = { component.onEvent(MainStore.Intent.OnClickOpenCalendar) }
        )
        if (showModalCalendar) {
            ModalCalendar(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.Center),
                onClickCansel = { component.onEvent(MainStore.Intent.OnClickCloseCalendar) },
                onClickOk = { component.onEvent(MainStore.Intent.OnClickApplyDate(it)) },
                currentDate = state.currentDate
            )
        }
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
    AnimatedVisibility(modifier = modifier, visible = isVisible) {
        Snackbar(
            modifier.padding(16.dp),
            backgroundColor = successGreen
        ) {
            Text(text = stringResource(MainRes.strings.elevator_called_successfully))
        }
    }
}

@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    reservedSeats: List<ReservedSeat>,
    onClickBook: () -> Unit,
    onClickShowMap: () -> Unit,
    onClickShowOptions: () -> Unit,
    onClickOpenCalendar: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Column (
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
                .padding(horizontal =  16.dp),
        ) {
            BookingInformation(
                reservedSeats = reservedSeats,
                onClickBook = onClickBook,
                onClickShowMap = onClickShowMap,
                onClickShowOptions = onClickShowOptions,
                onClickOpenCalendar = onClickOpenCalendar
            )
        }
    }
}

