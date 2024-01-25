package band.effective.office.elevator.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import io.github.aakira.napier.Napier

@Composable
fun MapView(map: Painter) {

    val rect = Rect(Offset(150f, 150f), Size(150f, 150f))

    Image(
        painter = map,
        contentDescription = null,
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures (
                    onTap = { offset ->
                        Napier.d { "tap on view $offset" }
                        if (rect.contains(offset))
                            Napier.d { "touch rect" }
                    }
                )
            }
            .drawBehind { drawSeats() }
    )
}

fun DrawScope.drawSeats() {
    drawRect(color = Color.Red, topLeft = Offset(150f, 150f), size = Size(150f, 150f))
}