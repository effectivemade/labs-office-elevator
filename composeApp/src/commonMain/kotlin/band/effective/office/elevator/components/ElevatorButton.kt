package band.effective.office.elevator.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.lightGray

@Composable
internal fun ElevatorButton(
    modifier: Modifier,
    scale: Float = 1f,
    width: Dp = 108.dp,
    height: Dp = 204.dp,
    checkedTrackColor: Color = MaterialTheme.colors.primary,
    uncheckedTrackColor: Color = MaterialTheme.colors.secondary,
    checkedThumbColor: Color = MaterialTheme.colors.secondary,
    uncheckedThumbColor: Color = MaterialTheme.colors.primary,
    gapBetweenThumbAndTrackEdge: Dp = 16.dp,
    isActive: Boolean,
    onButtonClick: () -> Unit
) {
    val thumbRadius = 33.dp

    val animatePosition = animateFloatAsState(
        targetValue =
        if (!isActive)
            with(LocalDensity.current) { (height - thumbRadius - gapBetweenThumbAndTrackEdge).toPx() }// = 155
        else
            with(LocalDensity.current) { (thumbRadius + gapBetweenThumbAndTrackEdge).toPx() }// = 49
    )

    Canvas(
        modifier = modifier
            .size(width = width, height = height)
            .scale(scale = scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        // This is called when the user taps on the canvas
                        if (!isActive)
                            onButtonClick()
                    }
                )
            }
            .border(width = 1.dp, shape = RoundedCornerShape(100.dp), color = lightGray)
    ) {
        // Track
        drawRoundRect(
            color = if (!isActive) checkedTrackColor else uncheckedTrackColor,
            cornerRadius = CornerRadius(100.dp.toPx())
        )

        // Thumb
        drawCircle(
            color = if (!isActive) checkedThumbColor else uncheckedThumbColor,
            radius = thumbRadius.toPx(),
            center = Offset(
                y = animatePosition.value,
                x = size.width / 2
            )
        )
    }
}
