package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.roomUiState.RoomInfoUiState
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents.PanelView
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents.RoomsView
import band.effective.office.tablet.ui.selectRoomScreen.SelectRoomComponent
import band.effective.office.tablet.ui.selectRoomScreen.SelectRoomScreen

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
fun FreeNegotiationsView(
    listRooms: List<RoomInfoUiState>,
    nameRoomCurrent: String,
    showBookingModal: Boolean,
    selectRoomComponent: SelectRoomComponent,
    onMainScreen: (reset: Boolean) -> Unit,
    onBookRoom: (name: RoomInfoUiState, maxDuration: Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        PanelView(
            modifier = Modifier
                .fillMaxHeight(0.2f)
                .background(MaterialTheme.colors.surface),
            nameRoom = nameRoomCurrent,
            onMainScreen = onMainScreen
        )
        RoomsView(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            listRooms = listRooms,
            onBookRoom = onBookRoom
        )
    }
    Box(modifier = Modifier.fillMaxSize()) {
        if(showBookingModal) {
            SelectRoomScreen(component = selectRoomComponent)
        }
    }
}
