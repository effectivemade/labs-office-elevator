package band.effective.office.tablet.ui.mainScreen.roomInfoComponents

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.utils.CalendarStringConverter
import java.util.Calendar

@Composable
fun FreeRoomInfoComponent(
    modifier: Modifier = Modifier,
    name: String,
    capacity: Int,
    isHaveTv: Boolean,
    electricSocketCount: Int,
    nextEvent: EventInfo?,
    timeToNextEvent: Int
) {
    CommonRoomInfoComponent(
        modifier = modifier,
        name = name,
        capacity = capacity,
        isHaveTv = isHaveTv,
        electricSocketCount = electricSocketCount,
        backgroundColor = LocalCustomColorsPalette.current.freeStatus
    ) {
        Text(
            text = MainRes.string.free_room_occupancy.format(
                time = nextEvent?.startTime?.time() ?: ""
            ),
            style = MaterialTheme.typography.h5
        )
        Text(
            text = "${MainRes.string.free_duration_string} $timeToNextEvent",
            style = MaterialTheme.typography.h5
        )
    }
}

private fun Calendar.time() = CalendarStringConverter.calendarToString(this, "HH:mm")