package band.effective.office.elevator.ui.booking.components.modals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedTheme

@Composable
fun BookingRepeatElement(selected: Boolean, bookingText: String, onSelect: () -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp
        ),
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        onClick = onSelect
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RadioButton(
                enabled = false,
                selected = selected,
                onClick = { },
                colors = RadioButtonDefaults.colors(
                    disabledSelectedColor = MaterialTheme.colors.primary
                )
            )
            Text(
                text = bookingText,
                style = MaterialTheme.typography.button.copy(
                    color = ExtendedTheme.colors.radioTextColor,
                    fontWeight = FontWeight(400)
                ),
                modifier = Modifier.fillMaxWidth().wrapContentHeight()
            )
        }
    }
}