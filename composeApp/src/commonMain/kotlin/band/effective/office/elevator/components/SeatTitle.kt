package band.effective.office.elevator.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.ui.models.ReservedSeat

@Composable
fun SeatTitle(seat: ReservedSeat) {
    Column {
        Text(
            text = seat.seatName,
            fontSize = 15.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = seat.bookingDay,
                fontSize = 15.sp,
                color = Color.Black
            )
            Divider(
                modifier = Modifier
                    .width(19.dp)
                    .rotate(90f),
                thickness = 2.dp,
            )
            Text(
                text = seat.bookingTime,
                fontSize = 15.sp,
                color = Color.Black
            )
        }
    }
}