package band.effective.office.tv.screen.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import band.effective.office.tv.screen.menu.MenuScreen

@Composable
fun NavigationHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Menu.name) {
        composable(Screen.Menu.name) {
            MenuScreen(
                itemsList = NavigationModel.screensList,
                navController = navController
            )
        }
        NavigationModel.screensList.forEach{ screen->
            composable(screen.screen.name) {screen.screenFun()}
        }
    }
}