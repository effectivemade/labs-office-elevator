package band.effective.office.tv.screen.navigation

import androidx.compose.foundation.background
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import band.effective.office.tv.screen.menu.MenuScreen
import com.example.effecticetv.ui.theme.BackgroundColor

@Composable
fun NavigationHost() {
    val navController = rememberNavController()
    val screensList = listOf("Events", "Best Photo", "History")
    NavHost(navController = navController, startDestination = "menu") {
        // TODO(Maksim Mishenko): replace text with desired screen
        composable("menu") {
            MenuScreen(
                modifier = Modifier.background(BackgroundColor),
                itemsList = screensList,
                onNavigate = {
                    navController.navigate(it)
                })
        }
        composable("Events") { Text("Events") }
        composable("Best Photo") { Text("Best Photo") }
        composable("History") { Text("History") }
    }
}