package band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.uiComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.RealDateTimeComponent
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.h8
import band.effective.office.tablet.utils.CalendarStringConverter
import io.github.skeptick.libres.compose.painterResource
import java.util.Calendar

@Composable
fun DateTimeView(modifier: Modifier, component: RealDateTimeComponent, selectDate: Calendar) {
    Column(modifier = modifier) {
        Text(
            text = MainRes.string.select_date_tine_title,
            color = LocalCustomColorsPalette.current.secondaryTextAndIcon,
            style = MaterialTheme.typography.h8
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxSize()) {
            Button(
                modifier = Modifier.fillMaxHeight().weight(1f).clip(RoundedCornerShape(15.dp)),
                onClick = { component.decrementDay() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LocalCustomColorsPalette.current.elevationBackground
                )
            ) {
                Text(
                    text = "<",
                    color = LocalCustomColorsPalette.current.tertiaryTextAndIcon,
                    style = MaterialTheme.typography.h8
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                modifier = Modifier.fillMaxHeight().weight(4f).clip(RoundedCornerShape(15.dp)),
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LocalCustomColorsPalette.current.elevationBackground
                )
            ) {
                Text(
                    text = selectDate.dateTime(),
                    style = MaterialTheme.typography.h6,
                    color = LocalCustomColorsPalette.current.primaryTextAndIcon
                )
                Spacer(Modifier.width(5.dp))
                Image(
                    modifier = Modifier,
                    painter = painterResource(MainRes.image.calendar),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = LocalCustomColorsPalette.current.primaryTextAndIcon)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                modifier = Modifier.fillMaxHeight().weight(1f).clip(RoundedCornerShape(15.dp)),
                onClick = { component.incrementDay() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LocalCustomColorsPalette.current.elevationBackground
                )
            ) {
                Text(
                    text = ">",
                    color = LocalCustomColorsPalette.current.tertiaryTextAndIcon,
                    style = MaterialTheme.typography.h6
                )
            }
        }
    }
}


private fun Calendar.time() = CalendarStringConverter.calendarToString(this, "HH:mm")
private fun Calendar.date() = CalendarStringConverter.calendarToString(this, "dd MMMM")
private fun Calendar.dateTime() =
    MainRes.string.start_date_time.format(time = time(), date = date())