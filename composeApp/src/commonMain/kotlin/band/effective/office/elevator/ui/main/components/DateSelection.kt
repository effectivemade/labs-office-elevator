package band.effective.office.elevator.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.MainRes
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun DateSelection(onClickOpenCalendar: () -> Unit, onClickOpenBottomDialog: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(MainRes.strings.nearest_bookings),
            fontSize = 15.sp,
            color = Color.Black,
            modifier = Modifier.wrapContentWidth()
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            CalendarTitle(onClickOpenCalendar = onClickOpenCalendar)
            FilterButton(onClickOpenBottomSheetDialog = onClickOpenBottomDialog)
        }
    }
}

@Composable
fun CalendarTitle(onClickOpenCalendar: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onClickOpenCalendar() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(MainRes.images.material_calendar_ic),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(MainRes.strings.by_date),
            color = MaterialTheme.colors.secondaryVariant,
            fontSize = 15.sp
        )
    }
}
