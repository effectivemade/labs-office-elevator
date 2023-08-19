package band.effective.office.elevator.ui.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.textGrayColor
import band.effective.office.elevator.ui.models.ReservedSeat

@Composable
fun SeatTitle(seat: ReservedSeat) {
    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = seat.seatName,
            style = MaterialTheme.typography.subtitle1.copy(color = Color.Black)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.wrapContentSize()
        ) {
            Text(
                text = seat.bookingDay,
                style = MaterialTheme.typography.subtitle1.copy(color = textGrayColor),
                modifier = Modifier.wrapContentSize()
            )
            Divider(
                modifier = Modifier
                    .width(6.dp)
                    .rotate(90f),
                thickness = 2.dp,
            )
            Text(
                text = seat.bookingTime,
                style = MaterialTheme.typography.subtitle1.copy(color = textGrayColor),
                modifier = Modifier.wrapContentSize()
            )
        }
    }
}
