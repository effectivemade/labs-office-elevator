package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents.roomCard

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.domain.model.RoomInfo

@RequiresApi(Build.VERSION_CODES.GINGERBREAD)
@Composable
fun RoomCard(
    modifier: Modifier,
    roomInfo: RoomInfo,
    isLessDuration: Boolean
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier.padding(horizontal =  30.dp),
        ) {
            NameRoom(roomInfo.name)
            Spacer(modifier = Modifier.height(10.dp))
            StateRoomView(
                roomInfo = roomInfo,
                isLessDuration = isLessDuration
            )
            Spacer(modifier = Modifier.height(20.dp))
            IconCharacteristicsRoom(
                modifier = Modifier
                   .padding(horizontal = 10.dp),
                roomInfo = roomInfo
            )
        }
    }
}