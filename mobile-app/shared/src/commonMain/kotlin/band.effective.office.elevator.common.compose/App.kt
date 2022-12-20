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
import band.effective.office.elevator.common.compose.screens.login.GoogleAuthorization
import band.effective.office.elevator.common.compose.screens.login.LoginScreen
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.PopUpTo
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
        val initialRoute =
            if (GoogleAuthorization.token.isNullOrBlank().not()) Routes.Home.toString()
            else Routes.Login.toString()
        NavHost(
            navigator = navigator,
            initialRoute = initialRoute
        ) {
            Routes.values().forEach { route ->
                scene(route.toString()) {
                    when (route) {
                        Routes.Home -> HomeScreen(
                            onSignOut = {
                                navigator.navigate(
                                    Routes.Login.toString(),
                                    NavOptions(
                                        launchSingleTop = true,
                                        popUpTo = PopUpTo.First(true)
                                    ),
                                )
                            }
                        )
                        Routes.Login -> LoginScreen(onSignInSuccess = {
                            navigator.navigate(
                                Routes.Home.toString(),
                                NavOptions(
                                    launchSingleTop = true,
                                    popUpTo = PopUpTo.First(true)
                                ),
                            )
                        })
                        Routes.About -> AboutScreen()
                    }
                }
            }
        }
    }
}