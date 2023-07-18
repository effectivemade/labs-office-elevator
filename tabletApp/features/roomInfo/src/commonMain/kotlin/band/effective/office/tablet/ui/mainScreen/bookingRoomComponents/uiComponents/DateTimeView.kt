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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.RealDateTimeComponent
import band.effective.office.tablet.utils.CalendarStringConverter
import io.github.skeptick.libres.compose.painterResource
import java.util.Calendar

@Composable
fun DateTimeView(modifier: Modifier, component: RealDateTimeComponent, selectDate: Calendar) {
    Column(modifier = modifier) {
        Text(
            text = MainRes.string.select_date_tine_title,
            color = Color(0xFF808080),
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxSize()) {
            Button(
                modifier = Modifier.fillMaxHeight().weight(1f).clip(RoundedCornerShape(15.dp)),
                onClick = { component.decrementDay() },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFFFFFFFF),
                    backgroundColor = Color(0xFF302D2C)
                )
            ) {
                Text(
                    text = "<",
                    color = Color(0xFF777777),
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                modifier = Modifier.fillMaxHeight().weight(4f).clip(RoundedCornerShape(15.dp)),
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFFFFFFFF),
                    backgroundColor = Color(0xFF302D2C)
                )
            ) {
                Text(
                    text = selectDate.dateTime(),
                    color = Color(0xFFFAFAFA),
                    fontSize = 20.sp
                )
                Spacer(Modifier.width(5.dp))
                Image(
                    modifier = Modifier,
                    painter = painterResource(MainRes.image.calendar),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                modifier = Modifier.fillMaxHeight().weight(1f).clip(RoundedCornerShape(15.dp)),
                onClick = { component.incrementDay() },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFFFFFFFF),
                    backgroundColor = Color(0xFF302D2C)
                )
            ) {
                Text(
                    text = ">",
                    color = Color(0xFF777777),
                    fontSize = 20.sp
                )
            }
        }
    }
}


private fun Calendar.time() = CalendarStringConverter.calendarToString(this, "HH:mm")
private fun Calendar.date() = CalendarStringConverter.calendarToString(this, "dd MMMM")
private fun Calendar.dateTime() =
    MainRes.string.start_date_time.format(time = time(), date = date())