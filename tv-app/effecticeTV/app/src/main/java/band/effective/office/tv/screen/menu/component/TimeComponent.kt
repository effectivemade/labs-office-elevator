package band.effective.office.tv.screen.menu.component

import android.graphics.Color
import android.widget.TextClock
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun TimeComponent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        AndroidView(
            factory = { context ->
                TextClock(context).apply {
                    format24Hour = "EEE, d MMMM HH:mm"
                    format12Hour = "EEE, d MMMM hh:mm a"
                    textSize = 17f
                    setTextColor(Color.WHITE)
                }
            }
        )
    }
}