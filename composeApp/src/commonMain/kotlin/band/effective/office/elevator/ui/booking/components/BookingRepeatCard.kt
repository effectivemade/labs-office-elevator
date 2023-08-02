package band.effective.office.elevator.ui.booking.components

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.MainRes
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun BookingRepeatCard(onSelected: () -> Unit) {
    val strings = listOf<String>(
        stringResource(MainRes.strings.do_not_repeat),
        stringResource(MainRes.strings.every_work_day),
        stringResource(MainRes.strings.every_week),
        stringResource(MainRes.strings.every_month),
        stringResource(MainRes.strings.another)
    )

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Color.White, shape = RoundedCornerShape(size = 16.dp))
            .padding(start = 16.dp, top = 12.dp, end = 24.dp, bottom = 12.dp)
    ) {
        for (element in strings) {
            BookingRepeatElement(
                bookingText = element,
                onSelect = onSelected
            )
        }
    }
}