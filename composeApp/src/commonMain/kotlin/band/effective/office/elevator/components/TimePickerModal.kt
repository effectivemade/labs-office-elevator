package band.effective.office.elevator.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedTheme
import band.effective.office.elevator.MainRes
import com.commandiron.wheel_picker_compose.WheelTimePicker
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import com.commandiron.wheel_picker_compose.utils.getCurrentTime
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.datetime.LocalTime

@Composable
fun TimePickerModal(
    titleText: String,
    modifier: Modifier = Modifier,
    onClickCansel: () -> Unit,
    onClickOk: (LocalTime) -> Unit
) {
    val selectorSettings = WheelPickerDefaults.selectorProperties(enabled = false)
    var currentTime = getCurrentTime()

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Text(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .padding(16.dp)
                .background(color = ExtendedTheme.colors.backgroundTextColor)
                .fillMaxWidth(),
            text = titleText,
            style = MaterialTheme.typography.h6
        )
        WheelTimePicker(
            modifier = Modifier.fillMaxWidth(),
            rowCount = 5,
            textColor = Color.Black,
            selectorProperties = selectorSettings,
            onSnappedTime = { newDate -> currentTime = newDate }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            EffectiveButton(
                modifier = Modifier
                    .fillMaxWidth(.5f),
                buttonText = stringResource(MainRes.strings.cansel),
                onClick = onClickCansel,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = MaterialTheme.colors.primary
                ),
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.primary),
                contentPadding = 0.dp,
                shape = RoundedCornerShape(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            EffectiveButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { onClickOk(currentTime) },
                buttonText = stringResource(MainRes.strings.ok),
                contentPadding = 0.dp,
                shape = RoundedCornerShape(40.dp)
            )
        }
    }

}