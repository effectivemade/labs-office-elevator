package band.effective.office.tv.screen.autoplay

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import band.effective.office.tv.screen.error.ErrorScreen
import band.effective.office.tv.screen.load.LoadScreen

@Composable
fun AutoplayScreen(viewModel: AutoplayViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    when{
        state.isLoading -> LoadScreen()
        state.isError ->  ErrorScreen("error")
        state.isLoaded -> viewModel.getScreen(state.currentScreen).invoke()
    }
}