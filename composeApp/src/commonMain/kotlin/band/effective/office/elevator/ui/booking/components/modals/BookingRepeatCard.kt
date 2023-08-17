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
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun BookingRepeatCard(onSelected: () -> Unit, modifier: Modifier = Modifier) {
    val strings = listOf(
        MainRes.strings.do_not_repeat,
        MainRes.strings.every_work_day,
        MainRes.strings.every_week,
        MainRes.strings.every_month,
        MainRes.strings.another
    )
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(strings[0]) }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .padding(all = 24.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = ExtendedThemeColors.colors.whiteColor, shape = RoundedCornerShape(size = 16.dp))
    ) {
        strings.forEach { frequency ->
            BookingRepeatElement(
                selected = frequency == selectedOption,
                bookingText = stringResource(frequency),
                onSelect = {
                    onOptionSelected(frequency)
                },
                onSelected = onSelected
            )
        }
    }
}