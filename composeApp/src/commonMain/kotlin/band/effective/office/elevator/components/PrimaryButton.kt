package band.effective.office.elevator.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

//use this components for rectangular buttons with 8 dp corners with white background
@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
    roundedCorner: Dp
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(roundedCorner),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary
        ),
        contentPadding = PaddingValues(all = 12.dp),
        onClick = onButtonClick,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.button
        )
    }
}