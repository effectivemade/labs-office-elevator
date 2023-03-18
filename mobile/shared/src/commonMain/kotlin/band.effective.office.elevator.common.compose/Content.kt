package band.effective.office.elevator.common.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import band.effective.office.elevator.common.compose.theme.OfficeElevatorTheme

internal val darkmodeState = mutableStateOf(false)
internal val safeAreaState = mutableStateOf(PaddingValues())
internal val SafeArea = compositionLocalOf { safeAreaState }
internal val DarkMode = compositionLocalOf { darkmodeState }

@Composable
internal fun Content() {
    OfficeElevatorTheme {
        App()
    }
    // isSystemInDarkTheme is not working correctly on iOS
    val darkMode = isSystemInDarkTheme()
    LaunchedEffect(key1 = Unit, block = {
        darkmodeState.value = darkMode
    })
}
