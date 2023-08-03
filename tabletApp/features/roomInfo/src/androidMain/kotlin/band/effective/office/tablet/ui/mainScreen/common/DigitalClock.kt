package band.effective.office.tablet.ui.mainScreen.common

import android.os.Build
import android.widget.TextClock
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
actual fun DigitalClock(
    format24Hour: String,
    format12Hour: String,
    textSize: Float,
    color: Int
) {
    AndroidView(
        factory = { context ->
            TextClock(context).apply {
                this.format24Hour = format24Hour
                this.format12Hour = format12Hour
                this.textSize = textSize
                setTextColor(color)
            }
        }
    )
}