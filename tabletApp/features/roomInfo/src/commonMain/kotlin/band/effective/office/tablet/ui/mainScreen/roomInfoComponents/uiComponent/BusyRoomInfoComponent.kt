package band.effective.office.tablet.ui.mainScreen.roomInfoComponents.uiComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.roomInfoColor
import band.effective.office.tablet.ui.theme.undefineStateColor
import band.effective.office.tablet.utils.CalendarStringConverter
import java.util.Calendar

@Composable
fun BusyRoomInfoComponent(
    modifier: Modifier = Modifier,
    name: String,
    capacity: Int,
    isHaveTv: Boolean,
    electricSocketCount: Int,
    event: EventInfo,
    onButtonClick: () -> Unit,
    timeToFinish: Int,
    isError: Boolean
) {
    val backgroundColor = LocalCustomColorsPalette.current.busyStatus

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val correctBackgroundColor = if (isError) undefineStateColor else Color.White
    val colorButton = if (isPressed) Color.Black else correctBackgroundColor
    val colorTextButton = if (isPressed) correctBackgroundColor else Color.Black

    Column {
        CommonRoomInfoComponent(
            modifier = modifier,
            name = name,
            capacity = capacity,
            isHaveTv = isHaveTv,
            electricSocketCount = electricSocketCount,
            backgroundColor = backgroundColor,
            isError = isError
        ) {
            Text(
                text = "${MainRes.string.room_occupancy_date.format(time = event.finishTime.time())} ${
                    if (timeToFinish > 0) MainRes.string.room_occupancy_time.format(
                        timeToFinish.getDuration()
                    ) else ""
                }",
                style = MaterialTheme.typography.h5,
                color = roomInfoColor
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = event.organizer.fullName,
                style = MaterialTheme.typography.h5,
                color = roomInfoColor
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        Button(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .clip(shape = RoundedCornerShape(70.dp))
                .height(60.dp)
                .fillMaxWidth()
                .background(color = Color.White).border(
                    width = 3.dp,
                    color = roomInfoColor,
                    shape = RoundedCornerShape(70.dp),
                ),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorButton
            ),
            interactionSource = interactionSource,
            onClick = onButtonClick
        ) {
            Text(text = MainRes.string.stop_meeting_button, color = colorTextButton)
        }
    }
}

private fun Calendar.time() = CalendarStringConverter.calendarToString(this, "HH:mm")