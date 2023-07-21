package band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.uiComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.ui.theme.alertColor
import band.effective.office.tablet.utils.CalendarStringConverter
import io.github.skeptick.libres.compose.painterResource
import java.util.Calendar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BusyAlertView(modifier: Modifier, event: EventInfo, onClick: () -> Unit) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier,
                painter = painterResource(MainRes.image.allert),
                contentDescription = null
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = MainRes.string.busy_time_string.format(
                    startTime = event.startTime.time(),
                    finishTime = event.finishTime.time(),
                    organizer = event.organizer
                ),
                color = alertColor,
                style = MaterialTheme.typography.h6
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Surface(color = MaterialTheme.colors.surface, onClick = { onClick() }) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = MainRes.string.see_free_room,
                    color = MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.h6
                )
                Image(
                    modifier = Modifier,
                    painter = painterResource(MainRes.image.arrow_to_right),
                    contentDescription = null
                )
            }
        }

    }
}

private fun Calendar.time() = CalendarStringConverter.calendarToString(this, "HH:mm")