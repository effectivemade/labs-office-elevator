package band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.uiComponents

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.h8
import band.effective.office.tablet.utils.CalendarStringConverter
import io.github.skeptick.libres.compose.painterResource
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
fun DateTimeView(
    modifier: Modifier,
    selectDate: Calendar,
    increment: () -> Unit,
    decrement: () -> Unit
) {
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
                onClick = { decrement() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LocalCustomColorsPalette.current.elevationBackground
                )
            ) {
                Image(
                    modifier = Modifier,
                    painter = painterResource(MainRes.image.arrow_left),
                    contentDescription = null
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
                    text =  selectDate.dateTime(),
                    style = MaterialTheme.typography.h6
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
                onClick = { increment() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LocalCustomColorsPalette.current.elevationBackground
                )
            ) {
                Image(
                    modifier = Modifier,
                    painter = painterResource(MainRes.image.arrow_right),
                    contentDescription = null
                )
            }
        }
    }
}


private fun Calendar.time() = CalendarStringConverter.calendarToString(this, "HH:mm")
private fun Calendar.date() = CalendarStringConverter.calendarToString(this, "dd MMMM")
private fun Calendar.dateTime() =
    MainRes.string.start_date_time.format(time = time(), date = date())