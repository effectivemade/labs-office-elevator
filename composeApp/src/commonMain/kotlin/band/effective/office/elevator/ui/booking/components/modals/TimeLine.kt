package band.effective.office.elevator.ui.booking.components.modals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ButtonElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedThemeColors

@Composable
fun TimeLine(
    date: String,
    time: String,
    elevation: ButtonElevation,
    onPickDate: () -> Unit,
    onPickTime: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
            .padding(start = 54.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ExtendedThemeColors.colors.transparentColor
            ),
            elevation = elevation,
            onClick = onPickDate
        ) {
            Text(
                text = date,
                style = MaterialTheme.typography.button.copy(
                    fontWeight = FontWeight(
                        weight = 400
                    ),
                    color = Color.Black
                )
            )
            Spacer(modifier = Modifier.width(width = 10.dp))
        }
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ExtendedThemeColors.colors.transparentColor
            ),
            elevation = elevation,
            onClick = onPickTime
        ) {
            Text(
                text = time,
                style = MaterialTheme.typography.button.copy(
                    fontWeight = FontWeight(
                        weight = 400
                    ),
                    color = ExtendedThemeColors.colors.blackColor
                )
            )
        }
    }
}