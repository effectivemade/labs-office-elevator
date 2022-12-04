package band.effective.office.elevator.common.compose.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import band.effective.office.elevator.common.compose.theme.colors.LocalColors
import band.effective.office.elevator.common.compose.theme.colors.OfficeElevatorColors
import band.effective.office.elevator.common.compose.theme.colors.defaultPalette

@Composable
fun OfficeElevatorTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColors provides defaultPalette,
        content = content
    )
}

object OfficeElevator {
    val color: OfficeElevatorColors
        @Composable
        get() = LocalColors.current
}