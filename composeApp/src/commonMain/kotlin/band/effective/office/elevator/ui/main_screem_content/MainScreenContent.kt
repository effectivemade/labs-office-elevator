package band.effective.office.elevator.ui.main_screem_content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.ElevatorButton
import band.effective.office.elevator.components.TitlePage
import band.effective.office.elevator.successGreen
import band.effective.office.elevator.ui.main_screem_content.components.BookingInformation
import band.effective.office.elevator.ui.main_screem_content.components.DateSelection
import band.effective.office.elevator.ui.main_screem_content.components.ElevatorUIComponent
import band.effective.office.elevator.ui.main_screem_content.store.ElevatorStore
import band.effective.office.elevator.ui.models.ElevatorState
import kotlinx.coroutines.delay

@Composable
fun MainScreenContent(component: MainScreenContentComponent) {

    val state by component.state.collectAsState()
    var isErrorMessageVisible by remember { mutableStateOf(false) }
    var isSuccessMessageVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf(MainRes.string.something_went_wrong) }

    LaunchedEffect(component) {
        component.label.collect { label ->
            when (label) {
                is ElevatorStore.Label.ShowError -> {
                    errorMessage = label.errorState.message ?: MainRes.string.something_went_wrong
                    isErrorMessageVisible = true
                    delay(3000)
                    isErrorMessageVisible = false
                }

                ElevatorStore.Label.ShowSuccess -> {
                    isSuccessMessageVisible = true
                    delay(3000)
                    isSuccessMessageVisible = false
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

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
            Text(text = MainRes.string.elevator_called_successfully)
        }
    }
}


@Composable
private fun ElevatorScreenContent(
    isButtonActive: Boolean,
    onElevatorButtonClick: (ElevatorStore.Intent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        ElevatorButton(
            modifier = Modifier.align(Alignment.Center),
            isActive = isButtonActive,
            onButtonClick = { onElevatorButtonClick(ElevatorStore.Intent.OnButtonClicked) }
        )
    }
}

@Composable
fun Test() {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        TitlePage(
            title = MainRes.string.main,
            modifier = Modifier.padding(top = 60.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        ElevatorUIComponent(
            elevatorState = ElevatorState.Below,
            onClickCallElevator = {}
        )
        Spacer(modifier = Modifier.height(24.dp))
        BookingInformation(
            reservedSeats = listOf(),
            onClickBook = {}
        )
    }
}