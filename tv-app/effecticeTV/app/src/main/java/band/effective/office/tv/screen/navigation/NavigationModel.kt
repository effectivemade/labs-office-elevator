package band.effective.office.tv.screen.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

// TODO(Maksim Mishenko): paste screens

data class NavigationModel(
    val screen: @Composable () -> Unit, val title: String
) {
    companion object {
        val screensList = listOf(
            NavigationModel(@Composable { LeaderIdEventScreen() }, "Events"),
            NavigationModel(@Composable { BestFotoScreen() }, "Best Photo"),
            NavigationModel(@Composable { HistoryScreen() }, "History")
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