package band.effective.office.elevator.ui.main.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.ElevatorUpButton
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun ElevatorUIComponent(
    onClickCallElevator: () -> Unit
) {
    Row(verticalAlignment =Alignment.CenterVertically) {
        Text(
            text = stringResource(MainRes.strings.elevator),
            fontSize = 15.sp, // TODO использовать тему
            color = Color.Black
        )
        Spacer(Modifier.weight(.1f))
        ActionCall(onClickCallElevator)
        }
}

@Composable
private fun ActionCall(onClickCallElevator: () -> Unit) {
    ElevatorUpButton(
        buttonText = stringResource(MainRes.strings.elevator_button),
        onClick = onClickCallElevator,
    )
}

