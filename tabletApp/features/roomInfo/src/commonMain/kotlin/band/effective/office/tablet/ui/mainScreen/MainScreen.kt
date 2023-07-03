package band.effective.office.tablet.ui.mainScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import band.effective.office.tablet.domain.model.RoomInfo

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

@Composable
fun MainScreenView(room: RoomInfo, onSelectOtherRoom: () -> Unit) {
    Column{
        Text(text = room.name)
        Button(onClick = { onSelectOtherRoom() }) {
            Text(text = "SelectOtherRoom")
        }
    }
}