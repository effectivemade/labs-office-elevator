package band.effective.office.tablet.ui.mainScreen.common

import androidx.compose.runtime.Composable

@Composable
expect fun DigitalClock(
    format24Hour: String,
    format12Hour: String,
    textSize: Float,
    color: Int
)