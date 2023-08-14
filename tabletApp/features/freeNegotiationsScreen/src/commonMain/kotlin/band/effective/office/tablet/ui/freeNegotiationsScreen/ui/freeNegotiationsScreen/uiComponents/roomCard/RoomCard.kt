package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents.roomCard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.roomUiState.RoomInfoUiState
@Composable
fun RoomCard(
    modifier: Modifier,
    roomInfo: RoomInfoUiState
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier.padding(horizontal =  30.dp),
        ) {
            NameRoom(roomInfo.room.name)
            Spacer(modifier = Modifier.height(10.dp))
            IconCharacteristicsRoom(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                roomInfo = roomInfo
            )
            Spacer(modifier = Modifier.height(20.dp))
            StateRoomView(roomInfo)
        }
    }
}