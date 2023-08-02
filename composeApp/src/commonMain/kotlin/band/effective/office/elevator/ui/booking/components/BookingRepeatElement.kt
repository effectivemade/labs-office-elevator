package band.effective.office.elevator.ui.booking.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedTheme

@Composable
fun BookingRepeatElement(bookingText: String, onSelect: () -> Unit) {
    val selected = remember { mutableStateOf(value = false) }
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(selected = selected.value, onClick = {
            selected.value = !selected.value
            onSelect
        })
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