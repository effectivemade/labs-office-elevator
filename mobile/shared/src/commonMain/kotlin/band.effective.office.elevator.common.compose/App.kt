package band.effective.office.elevator.common.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import band.effective.office.elevator.common.compose.navigation.RootScreen
import cafe.adriel.voyager.navigator.Navigator
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

@Composable
internal fun App() {
    Napier.base(DebugAntilog())
    Box(
        modifier = Modifier.background(MaterialTheme.colors.background)
            .padding(top = SafeArea.current.value.calculateTopPadding())
    ) {
        Navigator(screen = RootScreen())
    }
}