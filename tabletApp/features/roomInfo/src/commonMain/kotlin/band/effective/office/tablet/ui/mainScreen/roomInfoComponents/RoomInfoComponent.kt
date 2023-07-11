package band.effective.office.tablet.ui.mainScreen.roomInfoComponents

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

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
fun RoomInfoComponent(modifier: Modifier = Modifier, room: RoomInfo) {
    val paddings = 30.dp
    Column(modifier = modifier) {
        DateTimeComponent(modifier = Modifier.padding(paddings))
        when {
            room.isFree() -> {
                FreeRoomInfoComponent(
                    modifier = Modifier.padding(paddings),
                    name = room.name,
                    capacity = room.capacity,
                    isHaveTv = room.isHaveTv,
                    electricSocketCount = room.electricSocketCount,
                    nextEvent = room.eventList.firstOrNull()
                )
            }

            room.isBusy() -> {
                BusyRoomInfoComponent(
                    modifier = Modifier.padding(paddings),
                    name = room.name,
                    capacity = room.capacity,
                    isHaveTv = room.isHaveTv,
                    electricSocketCount = room.electricSocketCount,
                    event = room.currentEvent
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        RoomEventListComponent(modifier = Modifier.padding(paddings), eventsList = room.eventList)
    }
}

private fun band.effective.office.tablet.domain.model.RoomInfo.isFree() = currentEvent == null
private fun band.effective.office.tablet.domain.model.RoomInfo.isBusy() = currentEvent != null