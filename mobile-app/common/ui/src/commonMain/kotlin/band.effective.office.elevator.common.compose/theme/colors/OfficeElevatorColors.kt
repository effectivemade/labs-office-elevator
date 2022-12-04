package band.effective.office.elevator.common.compose.theme.colors

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class OfficeElevatorColors(
    val primary: Color,
    val secondary: Color,
    val primaryText: Color,
    val primaryBackground: Color,
    val secondaryText: Color,
    val secondaryBackground: Color,
    val tintColor: Color,
    val controlColor: Color,
    val errorColor: Color
)

val LocalColors = staticCompositionLocalOf<OfficeElevatorColors> {
    error("No colors provided")
}