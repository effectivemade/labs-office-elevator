package band.effective.office.elevator.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.MainRes

@Composable
fun EffectiveButton(
    buttonText: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
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