package band.effective.office.elevator.common.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import band.effective.office.elevator.common.compose.expects.notchPadding
import band.effective.office.elevator.common.compose.navigation.NavRoute
import band.effective.office.elevator.common.compose.screens.login.GoogleAuthorization
import band.effective.office.elevator.common.compose.screens.login.LoginScreen
import band.effective.office.elevator.common.compose.screens.login.LoginViewModel
import band.effective.office.elevator.common.compose.screens.main.MainCoordinator
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.PopUpTo
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
internal fun App() {
    Napier.base(DebugAntilog())
    val navigator = rememberNavigator()
    Box(
        modifier = Modifier.background(MaterialTheme.colors.background).notchPadding()
    ) {
        val initialRoute =
            if (GoogleAuthorization.token.isNullOrBlank().not()) NavRoute.Main.route
            else NavRoute.Login.route
        NavHost(
            navigator = navigator,
            initialRoute = initialRoute
        ) {
            scene(NavRoute.Login.route) {
                LoginScreen(onSignInSuccess = {
                    navigator.navigate(
                        NavRoute.Main.route,
                        NavOptions(
                            launchSingleTop = true,
                            popUpTo = PopUpTo.First(true)
                        ),
                    )
                }, LoginViewModel())
            }
            scene(NavRoute.Main.route) {
                MainCoordinator(onBack = {
                    navigator.navigate(
                        NavRoute.Login.route,
                        NavOptions(
                            launchSingleTop = true,
                            popUpTo = PopUpTo.First(true)
                        ),
                    )
                })
            }
        }
    }
}