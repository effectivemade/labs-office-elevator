package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents.roomCard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.features.freeNegotiationsScreen.MainRes
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.roomUiState.RoomInfoUiState
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.roomUiState.RoomState
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.utils.time24
import java.util.Calendar

@Composable
fun StateRoomView(
    roomInfo: RoomInfoUiState
) {
    Column {
        when (roomInfo.state) {
            RoomState.FREE -> RoomIsFree()
            RoomState.BUSY -> RoomIsBusy(
                changeEventTime = roomInfo.changeEventTime,
                timeFinish = roomInfo.room.currentEvent!!.finishTime,
                organizer = roomInfo.room.currentEvent!!.organizer.fullName
            )

            else -> {
                RoomIsSoonBusy(
                    changeEventTime = roomInfo.changeEventTime,
                    timeStart = roomInfo.room.eventList.first().startTime,
                    organizer = roomInfo.room.eventList.first().organizer.fullName
                )
            }
        }
    }
}

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

@Composable
fun RoomIsBusy(changeEventTime: Int, timeFinish: Calendar, organizer: String) {
    InfoStateRoom(
        state = MainRes.string.before_time.format(time = infoEvent(timeFinish)),
        color = LocalCustomColorsPalette.current.busyStatus
    )
    Spacer(modifier = Modifier.height(20.dp))
    OrganizerView(organizer)
    Spacer(modifier = Modifier.height(10.dp))
    InfoTimeNextEvent(MainRes.string.until_finish.format(time = getDuration(changeEventTime)))
}

@Composable
fun RoomIsSoonBusy(changeEventTime: Int, timeStart: Calendar, organizer: String) {
    InfoStateRoom(
        state = MainRes.string.at_time.format(time = infoEvent(timeStart)),
        color = MaterialTheme.colors.secondary
    )
    Spacer(modifier = Modifier.height(20.dp))
    OrganizerView(organizer)
    Spacer(modifier = Modifier.height(10.dp))
    InfoTimeNextEvent(MainRes.string.through_time.format(time = getDuration(changeEventTime)))
}

fun getDuration(changeEventTime: Int): String {
    val days = (changeEventTime / 60) / 24
    val hours = (changeEventTime / 60) % 24
    val minutes = changeEventTime % 60

    val daysString = if (days != 0) MainRes.string.days.format(days = days.toString()) else ""
    val hoursString = if (hours != 0) MainRes.string.hours.format(hours = hours.toString()) else ""
    val minutesString =
        if (minutes != 0) MainRes.string.minutes.format(minutes = minutes.toString()) else ""
    return "${daysString}${hoursString}${minutesString}"
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