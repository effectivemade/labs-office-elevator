package band.effective.office.tablet.ui.mainScreen.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.ui.mainScreen.components.roomInfoComponents.BusyRoomInfoComponent
import band.effective.office.tablet.ui.mainScreen.components.roomInfoComponents.DateTimeComponent
import band.effective.office.tablet.ui.mainScreen.components.roomInfoComponents.FreeRoomInfoComponent
import band.effective.office.tablet.ui.mainScreen.components.roomInfoComponents.RoomEventListComponent

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
fun RoomInfoComponent(modifier: Modifier = Modifier, room: RoomInfo) {
    Column(modifier = modifier) {
        DateTimeComponent(modifier = Modifier.padding(25.dp))
        when {
            room.isFree() -> {
                FreeRoomInfoComponent(
                    modifier = Modifier.padding(25.dp),
                    name = room.name,
                    capacity = room.capacity,
                    isHaveTv = room.isHaveTv,
                    electricSocketCount = room.electricSocketCount,
                    nextEvent = room.eventList.firstOrNull()
                )
            }

            room.isBusy() -> {
                BusyRoomInfoComponent(
                    modifier = Modifier.padding(25.dp),
                    name = room.name,
                    capacity = room.capacity,
                    isHaveTv = room.isHaveTv,
                    electricSocketCount = room.electricSocketCount,
                    event = room.currentEvent
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        RoomEventListComponent(modifier = Modifier.padding(25.dp), eventsList = room.eventList)
    }
}

private fun RoomInfo.isFree() = currentEvent == null
private fun RoomInfo.isBusy() = currentEvent != null