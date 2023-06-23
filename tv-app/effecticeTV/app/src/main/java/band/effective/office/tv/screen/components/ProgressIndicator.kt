package band.effective.office.tv.screen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier,
    elementModifier: Modifier = Modifier,
    count: Int,
    currentIndex: Int,
    elementColor: Color = MaterialTheme.colors.secondary,
    currentElementColor: Color = Color.White
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
        for (index in 0 until count) {
            val animatedColor =
                animateColorAsState(
                    if (index == currentIndex) currentElementColor else elementColor
                )
            Row {
                Box(
                    modifier = elementModifier
                        .width(8.dp)
                        .height(8.dp)
                        .background(animatedColor.value)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}