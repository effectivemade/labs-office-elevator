package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents.PanelView
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents.RoomsView

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
fun FreeNegotiationsView(
    listRooms: List<RoomInfo>,
    nameRoom: String,
    onMainScreen: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        PanelView(
            modifier = Modifier
                .fillMaxHeight(0.2f)
                .background(MaterialTheme.colors.surface),
            nameRoom = nameRoom,
            onMainScreen = onMainScreen
        )
        RoomsView(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            listRooms = listRooms
        )
    }
}
