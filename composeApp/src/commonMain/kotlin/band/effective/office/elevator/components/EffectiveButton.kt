package band.effective.office.elevator.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.MainRes

@Composable
fun EffectiveButton(
    buttonText: String,
    onClick: () -> Unit,
    shape: Shape = RoundedCornerShape(40.dp),
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.background
    ),
    contentPadding: Dp = 10.dp,
    border: BorderStroke? = null
) {
    Button(
        onClick = onClick,
        colors = colors,
        modifier = modifier,
        border = border,
        shape = shape
    ) {
        Text(
            text = buttonText,
            style = MaterialTheme.typography.button,
            modifier = Modifier.padding(vertical = contentPadding)
        )
    }
}