package band.effective.office.tablet.ui.mainScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import band.effective.office.tablet.ui.mainScreen.components.MainScreenView

@Composable
fun MainScreen(component: MainComponent) {
    val state by component.state.collectAsState()
    when{
        state.isError -> {}
        state.isLoad -> {}
        state.isData -> {
            MainScreenView(
                room = state.roomInfo,
                onSelectOtherRoom = { component.sendEvent(MainScreenEvent.OnCLick) }
            )
        }
    }
}