package band.effective.office.tv.utils

import androidx.compose.ui.graphics.Color

fun getColorFromHex(colorString: String): Color {
    return Color(android.graphics.Color.parseColor(colorString))
}