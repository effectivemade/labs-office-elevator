package band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.uiComponents

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.BookingRoomComponent
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.store.BookingStore
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.h8
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
import kotlinx.datetime.atTime
import kotlinx.datetime.number
import kotlinx.datetime.toDateTimePeriod
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.toLocalDateTime
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
                    displayDaysOfAdjacentMonths = true
                )
            ),
        ),
        selectedDates =
        listOf(
            LocalDate(
                selectedDateTime[Calendar.YEAR],
                selectedDateTime[Calendar.MONTH],
                selectedDateTime[Calendar.DAY_OF_MONTH]
            )
        ),
        selectionMode = EpicDatePickerState.SelectionMode.Single(1)
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

//                        val changeInDay =
//                            (updatedCalendar.get(Calendar.DAY_OF_YEAR) - stateDateTime.get(Calendar.DAY_OF_YEAR))

                        val changeInDay = if (epicDatePickerState.selectedDates.isNotEmpty()) {
                            epicDatePickerState.selectedDates.first().dayOfMonth
                        } else {
                            selectedDateTime[Calendar.DAY_OF_MONTH]
                        }
                        val intentDate = BookingStore.Intent.OnSetDay(changeInDay)

                        val changeInTimeMillis =
                            (updatedCalendar.timeInMillis - stateDateTime.selectDate.timeInMillis)
                        val intentTime = BookingStore.Intent.OnChangeTime(changeInTimeMillis)

                        bookingRoomComponent.bookingStore.accept(intentDate)
                        //bookingRoomComponent.bookingStore.accept(intentTime)



                        onCloseRequest()
                    },
                    colors = ButtonDefaults.buttonColors(
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
    Card() {
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
                dayOfMonthContent = CustomDayOfMonthContent
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
    Row() {
        Button(
            modifier = Modifier
                .size(32.dp),
            onClick = { onClickPreviousMonth() },

        ) {
            Text(
                text = "<",
                color = LocalCustomColorsPalette.current.tertiaryTextAndIcon,
                style = MaterialTheme.typography.h8
            )
        }
        Text(
            text = epicDatePickerState.selectedDates.first().month.name + ", " + epicDatePickerState.selectedDates.first().dayOfMonth.toString(),
            fontSize = 20.sp,
            color = Color.Black
        )

        Button(
            modifier = Modifier
                .size(32.dp),
            onClick = { onClickNextMonth() },

        ) {
            Text(
                text = "<",
                color = LocalCustomColorsPalette.current.tertiaryTextAndIcon,
                style = MaterialTheme.typography.h8
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
        fontSize = 20.sp,
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
        fontSize = 20.sp,
        maxLines = 1,
        color = if (isSelected) pickerState.config.selectionContentColor
        else pickerState.config.pagerConfig.basisConfig.contentColor
    )
}