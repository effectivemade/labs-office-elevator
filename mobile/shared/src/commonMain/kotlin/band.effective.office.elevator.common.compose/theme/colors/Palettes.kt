package band.effective.office.elevator.common.compose.theme.colors

import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

internal val defaultPalette = lightColors(
    primary = Color(255, 255, 255, 255),
    secondary = Color(149, 99, 255, 255),
    background = Color(0xFFF7F7F7),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.Black
)

internal val darkPalette = lightColors(
    primary = Color(0xFF0000),
    secondary = Color(149, 99, 255, 255),
    background = Color(0xFF434040),
    onPrimary = Color.LightGray,
    onSecondary = Color.LightGray,
    onBackground = Color.LightGray
)