package band.effective.office.elevator.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EffectiveButton(
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = Color.White
        ),
        modifier = modifier
            .clip(RoundedCornerShape(48.dp))
    ) {
        Text(
            text = buttonText,
            fontFamily = MaterialTheme.typography.button.fontFamily,
            style = MaterialTheme.typography.button,
            fontSize = 15.sp,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}