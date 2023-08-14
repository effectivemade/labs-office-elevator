package band.effective.office.tablet.ui.mainScreen.roomInfoComponents.uiComponent

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.ui.mainScreen.common.DigitalClock
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
fun DateTimeComponent(
    modifier: Modifier = Modifier
) {
    val colorClock = LocalCustomColorsPalette.current.primaryTextAndIcon.toArgb()
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        DigitalClock(
            format24Hour = "HH:mm",
            format12Hour = "hh:mm a",
            textSize = 30f,
            color = colorClock
        )
        Spacer(modifier = Modifier.width(10.dp))
        DigitalClock(
            format24Hour = "d MMMM, EEEE",
            format12Hour = "d MMMM, EEEE",
            textSize = 25f,
            color = colorClock
        )
    }
}