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
    //TODO(Artem Gruzdev): replace title screen in string res
    val screensList = listOf(
        NavigationModel(
            title = "Events",
            screen = Screen.Events
        ),
        NavigationModel(
            title = "Best Photo",
            screen = Screen.BestPhoto),
        NavigationModel(
            screen = Screen.History,
            title = "History"
        )
    )
    NavHost(navController = navController, startDestination = Screen.Main.name) {
        // TODO(Maksim Mishenko): replace text with desired screen
        composable(Screen.Main.name) {

            MenuScreen(
                itemsList = screensList,
                navController = navController
            )
        }
        composable(Screen.Events.name) { Text("Events") }
        composable(Screen.BestPhoto.name) { Text("Best Photo") }
        composable(Screen.History.name) { Text("History") }
    }
}