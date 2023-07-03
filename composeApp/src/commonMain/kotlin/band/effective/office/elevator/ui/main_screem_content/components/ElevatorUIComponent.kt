package band.effective.office.elevator.ui.main_screem_content.components

import androidx.compose.foundation.Image
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
import band.effective.office.elevator.ui.models.ElevatorState
import io.github.skeptick.libres.compose.painterResource

@Composable
fun ElevatorUIComponent(
    elevatorState: ElevatorState,
    modifier: Modifier = Modifier,
    onClickCallElevator: () -> Unit
) {

    val title = when(elevatorState) {
        ElevatorState.Below -> MainRes.string.elevator_below
        ElevatorState.Goes -> MainRes.string.elevator_goes
        ElevatorState.Raised -> MainRes.string.elevator_raised
    }

    Column(
        modifier = modifier
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
            ElevatorState.Raised -> ActionText(MainRes.string.elevator_ready)
        }
    }
}

@Composable
private fun ActionCall(onClickCallElevator: () -> Unit) {
    Button(
        onClick = onClickCallElevator,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.background
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(48.dp))
    ) {
        Text(
            text = MainRes.string.elevator_button,
            fontFamily = MaterialTheme.typography.button.fontFamily,
            fontSize = 15.sp,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}

@Composable
private fun ActionWait() {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,

    ){
        Image(
            painter = painterResource(MainRes.image.loading_icon),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = MainRes.string.elevator_wait,
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
