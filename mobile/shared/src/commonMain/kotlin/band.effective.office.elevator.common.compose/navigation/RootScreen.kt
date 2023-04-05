package band.effective.office.elevator.common.compose.navigation

import androidx.compose.runtime.Composable
import band.effective.office.elevator.common.compose.expects.showToast
import band.effective.office.elevator.common.compose.screens.login.GoogleAuthorization
import band.effective.office.elevator.common.compose.screens.login.LoginScreen
import cafe.adriel.voyager.core.lifecycle.main.MainScreenWrapper
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow

internal class RootScreen : Screen {
    @Composable
    override fun Content() {
        val rootNavigator = LocalNavigator.currentOrThrow
        val initialRoute =
            if (GoogleAuthorization.token.isNullOrBlank().not()) MainScreenWrapper()
            else LoginScreen(onSignInSuccess = {
                rootNavigator.replace(MainScreenWrapper())
            })

        Navigator(screen = initialRoute)
    }
}