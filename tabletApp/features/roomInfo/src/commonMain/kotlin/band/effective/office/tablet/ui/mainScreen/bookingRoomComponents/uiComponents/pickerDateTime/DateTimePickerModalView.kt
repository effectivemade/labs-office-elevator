package band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.uiComponents.pickerDateTime

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import epicarchitect.calendar.compose.basis.EpicMonth
import epicarchitect.calendar.compose.basis.config.rememberBasisEpicCalendarConfig
import epicarchitect.calendar.compose.datepicker.config.rememberEpicDatePickerConfig
import epicarchitect.calendar.compose.datepicker.state.EpicDatePickerState
import epicarchitect.calendar.compose.datepicker.state.rememberEpicDatePickerState
import epicarchitect.calendar.compose.pager.config.rememberEpicCalendarPagerConfig
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateTimePickerModalView(dateTimePickerComponent: DateTimePickerComponent, currentDate: Calendar) {
    DateTimePickerModalView(
        dateTimePickerComponent = dateTimePickerComponent,
        currentDate = currentDate,
        onCloseRequest = { dateTimePickerComponent.sendIntent(DateTimePickerStore.Intent.CloseModal()) },
        onSetDate = { day: Int, month: Int -> dateTimePickerComponent.sendIntent(DateTimePickerStore.Intent.OnSetDate(day, month)) }
    )
}

@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateTimePickerModalView(
    dateTimePickerComponent: DateTimePickerComponent,
    currentDate: Calendar,
    onCloseRequest: () -> Unit,
    onSetDate: (changedDay: Int, changedMonth: Int) -> Unit
) {
    val stateDateTime by dateTimePickerComponent.state.collectAsState()
    val selectedDateTime by remember { mutableStateOf(stateDateTime.selectDate) }


    val epicDatePickerState = rememberEpicDatePickerState(
        config = rememberEpicDatePickerConfig(
            pagerConfig = rememberEpicCalendarPagerConfig(
                basisConfig = rememberBasisEpicCalendarConfig(
                    displayDaysOfAdjacentMonths = true,
                    dayOfMonthViewHeight = 32.dp
                )
            ),
            selectionContainerColor = LocalCustomColorsPalette.current.pressedPrimaryButton,
        ),
        selectedDates = /*TODO Here problem with date*/
        listOf(
            LocalDate(
                currentDate[Calendar.YEAR],
                currentDate[Calendar.MONTH] + 1,
                currentDate[Calendar.DAY_OF_MONTH]
            )
        ),
        selectionMode = EpicDatePickerState.SelectionMode.Single(1),
        initialMonth = EpicMonth(
            year = currentDate[Calendar.YEAR],
            month = Month(currentDate[Calendar.MONTH] + 1))
    )

    Dialog(
        onDismissRequest = onCloseRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                //.size(800.dp,562.dp)
                .fillMaxHeight(0.6f)
                .fillMaxWidth(0.9f)
                .clip(RoundedCornerShape(3))
                .background(LocalCustomColorsPalette.current.elevationBackground)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.padding(16.dp)) {
                    DatePickerView(epicDatePickerState = epicDatePickerState)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val updatedCalendar = Calendar.getInstance()
                        updatedCalendar.timeInMillis = selectedDateTime.timeInMillis

                        val changeInDay = if (epicDatePickerState.selectedDates.isNotEmpty()) {
                            epicDatePickerState.selectedDates.first().dayOfMonth
                        } else {
                            selectedDateTime[Calendar.DATE]
                        }
                        val changeInMonth = if (epicDatePickerState.selectedDates.isNotEmpty()) {
                            epicDatePickerState.selectedDates.first().monthNumber - 1
                        } else {
                            selectedDateTime[Calendar.MONTH] - 1
                        }

//                        val changeInYear = if (epicDatePickerState.selectedDates.isNotEmpty()) {
//                            epicDatePickerState.selectedDates.first().year
//                        } else {
//                            selectedDateTime[Calendar.YEAR]
//                        }

                        onSetDate(changeInDay, changeInMonth)
                        onCloseRequest()
                    },
                    colors = buttonColors(
                        contentColor = Color.White
                    )
                ) {
                    Text("OK")
                }
            }
        }
    }
}
