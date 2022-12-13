package band.effective.office.elevator.common.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import band.effective.office.elevator.common.compose.navigation.Routes
import band.effective.office.elevator.common.compose.screens.about.AboutScreen
import band.effective.office.elevator.common.compose.screens.home.HomeScreen
import band.effective.office.elevator.common.compose.screens.login.LoginScreen
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
internal fun App() {
    val navigator = rememberNavigator()
    Box(
        modifier = Modifier.background(MaterialTheme.colors.background).padding(
            start = SafeArea.current.value.calculateStartPadding(LayoutDirection.Ltr),
            top = SafeArea.current.value.calculateTopPadding(),
            end = SafeArea.current.value.calculateEndPadding(LayoutDirection.Ltr)
        )
    ) {
        NavHost(navigator = navigator, initialRoute = Routes.Home.toString()) {
            Routes.values().forEach { route ->
                scene(route.toString()) {
                    when (route) {
                        Routes.Home -> HomeScreen()
                        Routes.Login -> LoginScreen()
                        Routes.About -> AboutScreen()
                    }
                }
            }
        }
    }
}