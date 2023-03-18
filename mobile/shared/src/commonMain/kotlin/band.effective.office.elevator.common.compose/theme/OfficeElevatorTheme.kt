package band.effective.office.elevator.common.compose.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import band.effective.office.elevator.common.compose.DarkMode
import band.effective.office.elevator.common.compose.theme.colors.defaultPalette

@Composable
internal fun OfficeElevatorTheme(
    content: @Composable () -> Unit
) {
    val colors = if (DarkMode.current.value) {
        defaultPalette
    } else {
        defaultPalette
    }
    MaterialTheme(
        colors = colors
    ) {
        content()
    }
}