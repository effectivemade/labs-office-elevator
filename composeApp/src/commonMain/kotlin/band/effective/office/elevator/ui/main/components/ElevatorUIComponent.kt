package band.effective.office.elevator.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.EffectiveButton
import band.effective.office.elevator.ui.models.ElevatorState
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun ElevatorUIComponent(
    elevatorState: ElevatorState,
    modifier: Modifier = Modifier,
    onClickCallElevator: () -> Unit
) {

    val title = when(elevatorState) {
        ElevatorState.Below -> stringResource(MainRes.strings.elevator_below)
        ElevatorState.Goes -> stringResource(MainRes.strings.elevator_goes)
        ElevatorState.Raised -> stringResource(MainRes.strings.elevator_raised)
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 24.dp, vertical = 16.dp),
    ) {
        Text(
            text = title,
            fontSize = 15.sp, // TODO использовать тему
            color = Color.Black
        )
        Spacer(Modifier.height(30.dp))
        when(elevatorState) {
            ElevatorState.Below -> ActionCall(onClickCallElevator)
            ElevatorState.Goes -> ActionWait()
            ElevatorState.Raised -> ActionText(
                action = stringResource(MainRes.strings.elevator_ready)
            )
        }
    }
}

@Composable
private fun ActionCall(onClickCallElevator: () -> Unit) {
    EffectiveButton(
        buttonText = stringResource(MainRes.strings.elevator_button),
        onClick = onClickCallElevator,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun ActionWait() {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,

    ){
        Image(
            painter = painterResource(MainRes.images.loading_ic),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = stringResource(MainRes.strings.elevator_wait),
            fontSize = 15.sp,
            color = MaterialTheme.colors.secondary
        )
    }
}

@Composable
private fun ActionText(action: String) {
    Text(
        text = action,
        modifier = Modifier.fillMaxSize(),
        textAlign = TextAlign.Center,
        fontSize = 15.sp,
        color = MaterialTheme.colors.secondary
    )
}
