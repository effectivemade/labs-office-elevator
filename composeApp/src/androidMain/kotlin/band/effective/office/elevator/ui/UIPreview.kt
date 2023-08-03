package band.effective.office.elevator.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import band.effective.office.elevator.textGrayColor
import com.commandiron.wheel_picker_compose.WheelTimePicker
import com.commandiron.wheel_picker_compose.core.WheelTextPicker
import com.commandiron.wheel_picker_compose.utils.getCurrentTime
import kotlinx.datetime.LocalTime

@Preview
@Composable
fun EmployeesScreenPreview() {

    WheelTimePicker(
        onSnappedTime = { time ->
            Log.d("TIME", time.toString())
        }
    )
}

