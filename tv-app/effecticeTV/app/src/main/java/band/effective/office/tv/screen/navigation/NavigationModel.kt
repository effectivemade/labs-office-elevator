package band.effective.office.tv.screen.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

// TODO(Maksim Mishenko): paste screens

enum class Screen {
    Menu, Events, BestPhoto, History
}

data class NavigationModel(
    val screen: Screen, val screenFun: @Composable () -> Unit, val title: String
) {
    companion object {
        val screensList = listOf(
            NavigationModel(Screen.Events,@Composable { LeaderIdEventScreen() }, "Events"),
            NavigationModel(Screen.BestPhoto,@Composable { BestFotoScreen() }, "Best Photo"),
            NavigationModel(Screen.History,@Composable { HistoryScreen() }, "History")
        )
    }
}

@Composable
fun LeaderIdEventScreen() {
    Text(text = "Events")
}

@Composable
fun BestFotoScreen() {
    Text("Best Photo")
}

@Composable
fun HistoryScreen() {
    Text("History")
}