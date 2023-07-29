package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents.roomCard

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.features.freeNegotiationsScreen.MainRes
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import java.util.Calendar
import java.util.concurrent.TimeUnit

@RequiresApi(Build.VERSION_CODES.GINGERBREAD)
@Composable
fun StateRoomView(
    roomInfo: RoomInfo
) {
    Column {
        if (roomInfo.currentEvent == null) {
            RoomIsFree(roomInfo.eventList.first().startTime)
        } else {
            RoomIsBusy(roomInfo.currentEvent!!.finishTime, roomInfo.currentEvent!!.organizer)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.GINGERBREAD)
@Composable
fun RoomIsFree(timeStart: Calendar) {
    val currentTime = Calendar.getInstance()

    Text(
        text = MainRes.string.until_next_event,
        style = MaterialTheme.typography.h6,
        color = LocalCustomColorsPalette.current.secondaryTextAndIcon
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = getDuration(currentTime, timeStart),
        style = MaterialTheme.typography.h5,
        color = LocalCustomColorsPalette.current.primaryTextAndIcon
    )
}

@RequiresApi(Build.VERSION_CODES.GINGERBREAD)
@Composable
fun RoomIsBusy(timeFinish: Calendar, organizer: String) {
    val currentTime = Calendar.getInstance()
    Text(
        text = organizer,
        style = MaterialTheme.typography.h5,
        color = LocalCustomColorsPalette.current.primaryTextAndIcon
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = MainRes.string.until_finish.format(time = getDuration(currentTime, timeFinish)),
        style = MaterialTheme.typography.h6,
        color = LocalCustomColorsPalette.current.secondaryTextAndIcon
    )
}


@RequiresApi(Build.VERSION_CODES.GINGERBREAD)
fun getDuration(start: Calendar, finish: Calendar): String {
    val ml = finish.timeInMillis - start.timeInMillis
    val minutesDifferent = TimeUnit.MILLISECONDS.toMinutes(ml).toInt()
    val days = (minutesDifferent / 60) / 24
    val hours = (minutesDifferent / 60) % 24
    val minutes = minutesDifferent % 60 + 1

    val daysString = if (days != 0) MainRes.string.days.format(days = days.toString()) else ""
    val hoursString = if (hours != 0) MainRes.string.hours.format(hours = hours.toString()) else ""
    val minutesString = if (minutes != 0) MainRes.string.minutes.format(minutes = minutes.toString()) else ""
    return "${daysString}${hoursString}${minutesString}"
}