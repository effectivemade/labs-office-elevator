package band.effective.office.elevator.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import epicarchitect.calendar.compose.basis.BasisDayOfMonthContent
import epicarchitect.calendar.compose.basis.BasisDayOfWeekContent
import epicarchitect.calendar.compose.basis.EpicMonth
import epicarchitect.calendar.compose.basis.config.LocalBasisEpicCalendarConfig
import epicarchitect.calendar.compose.basis.config.rememberMutableBasisEpicCalendarConfig
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
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

@Composable
fun Calendar(state: EpicDatePickerState) {

    val coroutineScope = rememberCoroutineScope()

    val pagerState = state.pagerState

    Column {
        CalendarTitle(
            selectedDate = if (state.selectedDates.isEmpty())
                pagerState.currentMonth.month.name
            else
                stringFormat(state.selectedDates.first()),
            onClickNextMonth = { scrollMonth(coroutineScope, pagerState, 1) },
            onClickPreviousMonth = { scrollMonth(coroutineScope, pagerState, -1) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        EpicDatePicker(
            state = state,
            dayOfMonthContent = CustomDayOfMonthContent,
            dayOfWeekContent = CustomDayOfWeekContent
        )
    }
}

@Composable
fun CalendarTitle(
    onClickPreviousMonth: () -> Unit,
    onClickNextMonth: () -> Unit,
    selectedDate: String
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clickable { onClickPreviousMonth() },
            tint = Color.Black
        )
        Text(
            text = selectedDate,
            fontSize = 20.sp,
            color = Color.Black
        )
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clickable { onClickNextMonth() },
            tint = Color.Black
        )
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

private fun stringFormat(date: LocalDate) =
    "${date.month.name}, ${date.dayOfMonth}".lowercase()

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

            is EpicDatePickerState.SelectionMode.Single -> date in selectedDays
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