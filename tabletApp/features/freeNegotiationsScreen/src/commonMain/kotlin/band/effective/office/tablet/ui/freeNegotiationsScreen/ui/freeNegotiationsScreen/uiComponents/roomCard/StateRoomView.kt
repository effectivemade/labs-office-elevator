package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents.roomCard

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.features.freeNegotiationsScreen.MainRes
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.utils.time24
import java.util.Calendar
import java.util.concurrent.TimeUnit

val currentTime: Calendar = Calendar.getInstance()
@RequiresApi(Build.VERSION_CODES.GINGERBREAD)
@Composable
fun StateRoomView(
    roomInfo: RoomInfo,
    isLessDuration: Boolean
) {
    Column {
        when {
            (roomInfo.currentEvent == null && isLessDuration) ->
                RoomIsFree()

            roomInfo.currentEvent != null -> RoomIsBusy(
                roomInfo.currentEvent!!.finishTime,
                roomInfo.currentEvent!!.organizer
            )

            else -> {
                RoomIsSoonBusy(roomInfo.eventList.first().startTime, roomInfo.eventList.first().organizer)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.GINGERBREAD)
@Composable
fun RoomIsFree() {
    InfoStateRoom(
        state = MainRes.string.may_occupy,
        color = LocalCustomColorsPalette.current.freeStatus
    )
    Spacer(modifier = Modifier.height(20.dp))
    OrganizerView(" ")
    Spacer(modifier = Modifier.height(10.dp))
    InfoTimeNextEvent(" ")
}

@RequiresApi(Build.VERSION_CODES.GINGERBREAD)
@Composable
fun RoomIsBusy(timeFinish: Calendar, organizer: String) {
    InfoStateRoom(
        state = infoEvent(timeFinish),
        color = LocalCustomColorsPalette.current.busyStatus
    )
    Spacer(modifier = Modifier.height(20.dp))
    OrganizerView(organizer)
    Spacer(modifier = Modifier.height(10.dp))
    InfoTimeNextEvent(MainRes.string.until_finish.format(time = getDuration(currentTime, timeFinish)))
}

@RequiresApi(Build.VERSION_CODES.GINGERBREAD)
@Composable
fun RoomIsSoonBusy(timeStart: Calendar, organizer: String){
    InfoStateRoom(
        state = MainRes.string.at_time.format(time = infoEvent(timeStart)),
        color = MaterialTheme.colors.secondary
    )
    Spacer(modifier = Modifier.height(20.dp))
    OrganizerView(organizer)
    Spacer(modifier = Modifier.height(10.dp))
    InfoTimeNextEvent(MainRes.string.through_time.format(time = getDuration(currentTime, timeStart)))
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
    val minutesString =
        if (minutes != 0) MainRes.string.minutes.format(minutes = minutes.toString()) else ""
    return "${daysString}${hoursString}${minutesString}"
}

@RequiresApi(Build.VERSION_CODES.GINGERBREAD)
fun checkDuration(startEvent: Calendar, newEventDuration: Int): Boolean {
    val deviation = 5
    val currentTime = Calendar.getInstance()
    val ml = startEvent.timeInMillis - currentTime.timeInMillis
    val minutesDifferent = TimeUnit.MILLISECONDS.toMinutes(ml).toInt()
    return minutesDifferent >= newEventDuration - deviation
}

fun infoEvent(time: Calendar): String {
    val timeInstance = Calendar.getInstance()
    val dateInstance =
        "${timeInstance.get(Calendar.DAY_OF_MONTH)}.${getMonth(timeInstance.get(Calendar.MONTH))}"

    val eventDate = "${time.get(Calendar.DAY_OF_MONTH)}.${getMonth(time.get(Calendar.MONTH))}"
    val eventTime = time.time24()

    return if (dateInstance == eventDate) eventTime
    else {
        "$eventDate $eventTime"
    }
}