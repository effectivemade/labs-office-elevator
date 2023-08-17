package band.effective.office.tablet.ui.mainScreen.roomInfoComponents.uiComponent

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.roomInfoColor
import band.effective.office.tablet.utils.CalendarStringConverter
import java.util.Calendar

@Composable
fun FreeRoomInfoComponent(
    modifier: Modifier = Modifier,
    name: String,
    capacity: Int,
    isHaveTv: Boolean,
    electricSocketCount: Int,
    nextEvent: EventInfo,
    timeToNextEvent: Int,
    isError: Boolean,
) {
    CommonRoomInfoComponent(
        modifier = modifier,
        name = name,
        capacity = capacity,
        isHaveTv = isHaveTv,
        electricSocketCount = electricSocketCount,
        backgroundColor = LocalCustomColorsPalette.current.freeStatus,
        isError = isError
    ) {
        if (nextEvent != EventInfo.emptyEvent && timeToNextEvent > 0) {
            Text(
                text = MainRes.string.free_room_occupancy.format(
                    time = nextEvent.startTime.time(),
                    duration = timeToNextEvent.getDuration()
                ),
                style = MaterialTheme.typography.h5,
                color = roomInfoColor
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = nextEvent.organizer.fullName,
                style = MaterialTheme.typography.h5,
                color = roomInfoColor
            )
        }
    }
}

private fun Calendar.time() = CalendarStringConverter.calendarToString(this, "HH:mm")