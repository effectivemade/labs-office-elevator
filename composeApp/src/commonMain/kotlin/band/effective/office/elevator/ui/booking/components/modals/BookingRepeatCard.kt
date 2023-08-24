package band.effective.office.elevator.ui.booking.components.modals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.domain.models.BookingPeriod
import band.effective.office.elevator.domain.models.DayOfWeek
import band.effective.office.elevator.ui.booking.models.Frequency
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun BookingRepeatCard(
    onSelected: (Pair<String, BookingPeriod>) -> Unit,
    modifier: Modifier,
    frequency: Frequency
) {

    val days: List<DayOfWeek> = frequency.getDays()
    val strings = listOf(
        Pair(first = MainRes.strings.do_not_repeat, second = BookingPeriod.NoPeriod),
        Pair(first = MainRes.strings.every_work_day, second = BookingPeriod.EveryWorkDay(5)),
        Pair(first = MainRes.strings.every_week, second = BookingPeriod.Week(7, days)),
        Pair(
            first = MainRes.strings.every_month, second = BookingPeriod.Month(31),
        ),
        Pair(first = MainRes.strings.another, second = BookingPeriod.Another)
    )

    val (selectedOption, onOptionSelected) = remember { mutableStateOf(strings[0]) }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .padding(all = 24.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = ExtendedThemeColors.colors.whiteColor,
                shape = RoundedCornerShape(size = 16.dp)
            )
    ) {
        strings.forEachIndexed { index, pair ->
            val name: String = stringResource(pair.first)

            BookingRepeatElement(
                selected = pair == selectedOption,
                bookingText = name,
                onSelect = { it ->
                    onOptionSelected(pair)
                },
                onSelected = onSelected,
                bookingPeriod = pair.second
            )
        }
    }
}