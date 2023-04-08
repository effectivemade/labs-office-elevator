package band.effective.office.elevator.common.compose.navigation.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import band.effective.office.elevator.common.compose.navigation.tabs.internal.Tab
import band.effective.office.elevator.common.compose.navigation.tabs.internal.TabOptions
import band.effective.office.elevator.common.compose.screens.login.LoginScreen
import band.effective.office.elevator.common.compose.screens.main.MainScreenWrapper
import band.effective.office.elevator.common.compose.screens.profile.ProfileScreen
import band.effective.office.elevator.common.compose.screens.profile.ProfileScreenViewModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator

internal object ProfileTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Profile"
            val icon = rememberVectorPainter(Icons.Filled.Person)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel: ProfileScreenViewModel = rememberScreenModel { ProfileScreenViewModel() }
        val parentNavigator = LocalNavigator.current?.parent
        ProfileScreen(viewModel = viewModel, onSignOut = {
            parentNavigator?.replaceAll(LoginScreen(onSignInSuccess = {
                parentNavigator.replace(
                    MainScreenWrapper()
                )
            }))
        })
    }
}