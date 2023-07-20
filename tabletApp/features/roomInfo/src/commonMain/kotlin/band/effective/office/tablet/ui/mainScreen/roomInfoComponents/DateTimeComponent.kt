package band.effective.office.tablet.ui.mainScreen.roomInfoComponents

import android.os.Build
import android.widget.TextClock
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
fun DateTimeComponent(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        AndroidView(
            factory = { context ->
                TextClock(context).apply {
                    format24Hour = "HH:mm"
                    format12Hour = "hh:mm a"
                    textSize = 30f
                    setTextColor(android.graphics.Color.WHITE)
                }
            }
        )
        Spacer(modifier = Modifier.width(10.dp))
        AndroidView(
            factory = { context ->
                TextClock(context).apply {
                    format24Hour = "d MMMM, EEEE"
                    format12Hour = "d MMMM, EEEE"
                    textSize = 25f
                    setTextColor(android.graphics.Color.WHITE)
                }
            }
        )
    }
}