package band.effective.office.elevator.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import band.effective.office.elevator.ExtendedTheme
import band.effective.office.elevator.components.TimePickerModal
import band.effective.office.elevator.textGrayColor
import com.commandiron.wheel_picker_compose.WheelTimePicker
import com.commandiron.wheel_picker_compose.core.WheelTextPicker
import com.commandiron.wheel_picker_compose.utils.getCurrentTime
import kotlinx.datetime.LocalTime

@Preview
@Composable
fun EmployeesScreenPreview() {
    ExtendedTheme {
        TimePickerModal(
            modifier = Modifier.fillMaxWidth(.9f),
            titleText = "Бронирование до",
            onClickCansel = {},
            onClickOk = {Log.d("TIME", it.toString())}
        )
    }
}

