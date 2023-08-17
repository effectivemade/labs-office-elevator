package band.effective.office.tablet.ui.bookingComponents.pickerDateTime

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.features.core.MainRes
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.header6
import com.commandiron.wheel_picker_compose.WheelTimePicker
import kotlinx.datetime.LocalTime
import java.util.Calendar

@Composable
fun TimePickerView(currentDate: Calendar, selectedTime: Calendar) {
    Box(
        modifier = Modifier.fillMaxWidth(0.6f)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .background(
                        LocalCustomColorsPalette.current.mountainBackground,
                        RoundedCornerShape(12.dp)
                    )
                    .fillMaxWidth().fillMaxHeight(0.15f)
                    .clip(RoundedCornerShape(12.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = MainRes.string.timepicker_view_title,
                    style = header6,
                    color = LocalCustomColorsPalette.current.primaryTextAndIcon
                )
            }

            WheelTimePicker(
                modifier = Modifier.fillMaxWidth(1f),
                size = DpSize(248.dp, 260.dp),
                textStyle = header6,
                textColor = LocalCustomColorsPalette.current.primaryTextAndIcon,
                rowCount = 7,
                startTime = LocalTime(
                    currentDate[Calendar.HOUR_OF_DAY],
                    currentDate[Calendar.MINUTE]
                ),
                onSnappedTime = { time ->
                    selectedTime.set(Calendar.HOUR_OF_DAY, time.hour)
                    selectedTime.set(Calendar.MINUTE, time.minute)
                }
            )
        }
    }
}