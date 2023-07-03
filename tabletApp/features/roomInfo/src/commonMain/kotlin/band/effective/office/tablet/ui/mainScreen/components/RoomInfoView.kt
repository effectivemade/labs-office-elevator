package band.effective.office.tablet.ui.mainScreen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.ui.mainScreen.components.roomInfoComponents.BusyRoomInfoView
import band.effective.office.tablet.ui.mainScreen.components.roomInfoComponents.DateTimeView
import band.effective.office.tablet.ui.mainScreen.components.roomInfoComponents.FreeRoomInfoView
import band.effective.office.tablet.ui.mainScreen.components.roomInfoComponents.RoomEventListView

@Composable
fun RoomInfoView(modifier: Modifier = Modifier, room: RoomInfo) {
    Column(modifier = modifier) {
        DateTimeView(modifier = Modifier.padding(25.dp))
        when {
            room.isFree() -> {
                FreeRoomInfoView(
                    name = room.name,
                    capacity = room.capacity,
                    isHaveTv = room.isHaveTv,
                    electricSocketCount = room.electricSocketCount,
                    nextEvent = room.eventList.firstOrNull()
                )
            }
            room.isBusy() -> {
                BusyRoomInfoView(
                    name = room.name,
                    capacity = room.capacity,
                    isHaveTv = room.isHaveTv,
                    electricSocketCount = room.electricSocketCount,
                    event = room.currentEvent
                )
            }
        }
        RoomEventListView(room.eventList)
    }
}

private fun RoomInfo.isFree() = currentEvent == null
private fun RoomInfo.isBusy() = currentEvent != null