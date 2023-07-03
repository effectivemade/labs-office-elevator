package band.effective.office.tablet.ui.mainScreen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import band.effective.office.tablet.domain.model.RoomInfo

@Composable
fun MainScreenView(room: RoomInfo, onSelectOtherRoom: () -> Unit) {
    /*NOTE(Maksim Mishenko):
    * infoViewWidth is part of the width occupied by roomInfoView
    * infoViewWidth = infoViewFrame.width / mainScreenFrame.width
    * where infoViewFrame, mainScreenFrame is frames from figma and all width I get from figma*/
    val infoViewWidth = 627f / 1133f
    Row(modifier = Modifier.fillMaxSize()) {
        RoomInfoView(
            modifier = Modifier.fillMaxHeight().fillMaxWidth(infoViewWidth),
            room = room)
        BookingRoomView(onSelectOtherRoom)
    }
}