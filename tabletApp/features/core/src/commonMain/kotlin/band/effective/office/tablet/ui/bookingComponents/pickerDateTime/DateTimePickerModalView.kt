package band.effective.office.tablet.ui.bookingComponents.pickerDateTime

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import band.effective.office.tablet.ui.common.CrossButtonView
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.header8
import band.effective.office.tablet.utils.date
import band.effective.office.tablet.utils.time24
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
        selectedDates =
        listOf(
            LocalDate(
                year = currentDate[Calendar.YEAR],
                monthNumber = currentDate[Calendar.MONTH] + 1,
                dayOfMonth = currentDate[Calendar.DAY_OF_MONTH],
            )
        ),
        selectionMode = EpicDatePickerState.SelectionMode.Single(1),
        initialMonth = EpicMonth(
            year = currentDate[Calendar.YEAR],
            month = Month(currentDate[Calendar.MONTH] + 1))
    )


    DateTimePickerModalView(
        currentDate = currentDate,
        epicDatePickerState = epicDatePickerState,
        selectedDateTime = selectedDateTime,
        onCloseRequest = { dateTimePickerComponent.sendIntent(DateTimePickerStore.Intent.CloseModal()) },
        onSetDate = { day: Int, month: Int, year: Int, hour: Int, minute: Int ->
            dateTimePickerComponent.sendIntent(DateTimePickerStore.Intent.OnSetDate(day, month, year, hour, minute)) }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateTimePickerModalView(
    currentDate: Calendar,
    epicDatePickerState: EpicDatePickerState,
    selectedDateTime: Calendar,
    onCloseRequest: () -> Unit,
    onSetDate: (changedDay: Int, changedMonth: Int, changedYear: Int, changedHour: Int, changedMinute: Int) -> Unit
) {
    Dialog(
        onDismissRequest = onCloseRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .fillMaxWidth(0.8f)
                .clip(RoundedCornerShape(3))
                .background(LocalCustomColorsPalette.current.elevationBackground)
        ) {
            Column(
                modifier = Modifier.padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CrossButtonView(
                    onDismissRequest = onCloseRequest,
                    modifier = Modifier.fillMaxWidth(1f)
                )
                Row(
                    modifier = Modifier.padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DatePickerView(epicDatePickerState = epicDatePickerState)
                    Spacer(modifier = Modifier.width(40.dp))
                    TimePickerView(currentDate = currentDate, selectedTime = selectedDateTime)
                }
                /*TODO LOGIC MUST BE IN COMPONENT OR STORE */
                selectedDateTime.set(
                    /* year = */  if (epicDatePickerState.selectedDates.isNotEmpty()) epicDatePickerState.selectedDates.first().year else selectedDateTime[Calendar.YEAR],
                    /* month = */ if (epicDatePickerState.selectedDates.isNotEmpty()) epicDatePickerState.selectedDates.first().monthNumber - 1  else selectedDateTime[Calendar.MONTH],
                    /* date = */  if (epicDatePickerState.selectedDates.isNotEmpty()) epicDatePickerState.selectedDates.first().dayOfMonth else selectedDateTime[Calendar.DATE],
                    /* hourOfDay = */  selectedDateTime[Calendar.HOUR_OF_DAY],
                    /* minute = */selectedDateTime[Calendar.MINUTE]
                )
                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    modifier = Modifier
                        //.fillMaxHeight(1f)
                        .fillMaxWidth(0.3f),
                    onClick = {
                        onSetDate(
                            selectedDateTime[Calendar.DATE],
                            selectedDateTime[Calendar.MONTH],
                            selectedDateTime[Calendar.YEAR],
                            selectedDateTime[Calendar.HOUR_OF_DAY],
                            selectedDateTime[Calendar.MINUTE]
                        )
                        onCloseRequest()
                    },
                    colors = buttonColors(
                        containerColor = LocalCustomColorsPalette.current.pressedPrimaryButton
                    )
                ) {
                    Text(
                        text = selectedDateTime.date() + " с ${selectedDateTime.time24()}",
                        style = header8,
                        color = LocalCustomColorsPalette.current.primaryTextAndIcon,
                    )
                }
            }
        }
    }
}
