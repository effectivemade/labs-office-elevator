package band.effective.office.tablet.ui.mainScreen.components.roomInfoComponents

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.utils.CalendarStringConverter
import java.util.Calendar

@Composable
fun BusyRoomInfoComponent(
    name: String, capacity: Int, isHaveTv: Boolean, electricSocketCount: Int, event: EventInfo?
) {
    RoomInfoComponent(
        name = name,
        capacity = capacity,
        isHaveTv = isHaveTv,
        electricSocketCount = electricSocketCount,
        roomOccupancy = MainRes.string.room_occupancy.format(
            startTime = event?.startTime?.time() ?: "",
            finishTime = event?.finishTime?.time() ?: "",
            organizer = event?.organizer ?: ""
        ),
        backgroundColor = Color(0xFFF94C4C)
    )
}

private fun Calendar.time() = CalendarStringConverter.calendarToString(this, "HH:mm")