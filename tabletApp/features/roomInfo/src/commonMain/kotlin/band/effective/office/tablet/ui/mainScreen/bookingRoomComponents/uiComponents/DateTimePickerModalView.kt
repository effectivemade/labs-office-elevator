package band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.uiComponents

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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.BookingRoomComponent
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.header4
import band.effective.office.tablet.ui.theme.header6
import epicarchitect.calendar.compose.basis.BasisDayOfMonthContent
import epicarchitect.calendar.compose.basis.BasisDayOfWeekContent
import epicarchitect.calendar.compose.basis.EpicMonth
import epicarchitect.calendar.compose.basis.config.LocalBasisEpicCalendarConfig
import epicarchitect.calendar.compose.basis.config.rememberBasisEpicCalendarConfig
import epicarchitect.calendar.compose.basis.contains
import epicarchitect.calendar.compose.basis.localized
import epicarchitect.calendar.compose.basis.state.LocalBasisEpicCalendarState
import epicarchitect.calendar.compose.datepicker.EpicDatePicker
import epicarchitect.calendar.compose.datepicker.config.rememberEpicDatePickerConfig
import epicarchitect.calendar.compose.datepicker.state.EpicDatePickerState
import epicarchitect.calendar.compose.datepicker.state.LocalEpicDatePickerState
import epicarchitect.calendar.compose.datepicker.state.rememberEpicDatePickerState
import epicarchitect.calendar.compose.pager.config.rememberEpicCalendarPagerConfig
import epicarchitect.calendar.compose.pager.state.EpicCalendarPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import java.util.*

@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateTimePickerModalView(
    bookingRoomComponent: BookingRoomComponent,
    onCloseRequest: () -> Unit
) {
    val stateDateTime by bookingRoomComponent.state.collectAsState()
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
                selectedDateTime[Calendar.YEAR],
                selectedDateTime[Calendar.MONTH] + 1,
                selectedDateTime[Calendar.DAY_OF_MONTH]
            )
        ),
        selectionMode = EpicDatePickerState.SelectionMode.Single(1),
        initialMonth = EpicMonth(
            year = stateDateTime.selectDate[Calendar.YEAR],
            month = Month(stateDateTime.selectDate[Calendar.MONTH] + 1))
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

                        //val changeInTimeMillis = (updatedCalendar.timeInMillis - stateDateTime.selectDate.timeInMillis)


                        bookingRoomComponent.dateTimeComponent.changeCurrentDay(changeInDay)
                        bookingRoomComponent.dateTimeComponent.changeCurrentMonth(changeInMonth)

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

@Composable
private fun DatePickerView(epicDatePickerState: EpicDatePickerState) {
    val coroutineScope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxWidth(0.35f)) {
        Column {
            DatePickerTitleView(
                epicDatePickerState = epicDatePickerState,
                onClickNextMonth = {
                    scrollMonth(
                        coroutineScope = coroutineScope,
                        pagerState = epicDatePickerState.pagerState,
                        amount = 1
                    )
                },
                onClickPreviousMonth = {
                    scrollMonth(
                        coroutineScope = coroutineScope,
                        pagerState = epicDatePickerState.pagerState,
                        amount = -1
                    )
                }
            )
            EpicDatePicker(
                state = epicDatePickerState,
                dayOfWeekContent = CustomDayOfWeekContent,
                dayOfMonthContent = CustomDayOfMonthContent,
                modifier = Modifier.background(Color.Transparent),
            )
        }
    }
}

@Composable
private fun DatePickerTitleView(
    epicDatePickerState: EpicDatePickerState,
    onClickNextMonth: () -> Unit,
    onClickPreviousMonth: () -> Unit
) {
    Row(modifier = Modifier
        .background(LocalCustomColorsPalette.current.mountainBackground, RoundedCornerShape(12.dp))
        .fillMaxWidth().fillMaxHeight(0.15f)
        .clip(RoundedCornerShape(12.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            modifier = Modifier.fillMaxHeight(),
            onClick = { onClickPreviousMonth() },
            colors = buttonColors(
                containerColor = Color.Transparent,
            )

        ) {
            Text(
                text = " < ",
                style = header4,
                color = LocalCustomColorsPalette.current.tertiaryTextAndIcon
            )
        }
        Text(
            text = if (epicDatePickerState.selectedDates.isNotEmpty()) {
                epicDatePickerState.selectedDates.firstOrNull()!!.month.name.toLowerCase() +
                ", " + epicDatePickerState.selectedDates.firstOrNull()!!.dayOfMonth.toString()
            } else {
             epicDatePickerState.pagerState.currentMonth.month.name.toLowerCase()
             },
            style = header6,
            color = LocalCustomColorsPalette.current.primaryTextAndIcon,
        )

        Button(
            modifier = Modifier.fillMaxHeight(),
            onClick = { onClickNextMonth() },
            colors = buttonColors(
                containerColor = Color.Transparent,
            )

        ) {
            Text(
                text = " > ",
                color = LocalCustomColorsPalette.current.tertiaryTextAndIcon,
                style = header4,
            )
        }
    }
}

private fun scrollMonth(
    coroutineScope: CoroutineScope,
    pagerState: EpicCalendarPagerState,
    amount: Int
) {
    coroutineScope.launch {
        pagerState.scrollMonths(amount)
    }
}

private val CustomDayOfWeekContent: BasisDayOfWeekContent = { dayOfWeek ->
    val config = LocalBasisEpicCalendarConfig.current
    Text(
        text = dayOfWeek.localized(),
        textAlign = TextAlign.Center,
        color = config.contentColor,
        style = header6,
        maxLines = 1
    )
}

private val CustomDayOfMonthContent: BasisDayOfMonthContent = { date ->
    val basisState = LocalBasisEpicCalendarState.current!!
    val pickerState = LocalEpicDatePickerState.current!!
    val selectedDays = pickerState.selectedDates
    val selectionMode = pickerState.selectionMode

    val isSelected = remember(selectionMode, selectedDays, date) {
        when (selectionMode) {
            is EpicDatePickerState.SelectionMode.Range -> {
                if (selectedDays.isEmpty()) false
                else date in selectedDays.min()..selectedDays.max()
            }

            is EpicDatePickerState.SelectionMode.Single -> {
                date in selectedDays}
        }
    }

    Text(
        modifier = Modifier.alpha(
            if (date in basisState.currentMonth) 1.0f
            else 0.5f
        ),
        text = date.dayOfMonth.toString(),
        textAlign = TextAlign.Center,
        style = header6,
        maxLines = 1,
        color = if (isSelected) pickerState.config.selectionContentColor
        else pickerState.config.pagerConfig.basisConfig.contentColor
    )
}