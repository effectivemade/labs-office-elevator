package band.effective.office.elevator.ui.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.textGrayColor
import band.effective.office.elevator.textInBorderPurple
import band.effective.office.elevator.ui.models.ReservedSeat
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun BookingCard(
    seat: ReservedSeat,
    onClickShowOptions: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .fillMaxWidth()
    ) {
        Row {
            SeatIcon()
            Spacer(modifier = Modifier.width(12.dp))
            SeatTitle(seat)
            Spacer(modifier = Modifier.weight(.1f))
            Button(
                onClick = onClickShowOptions,
                elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = MaterialTheme.colors.primary)
            ) {
            Icon(
                painter = painterResource(MainRes.images.mi_options_vertical),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = textInBorderPurple
            )
            }
        }
    }
}

@Composable
private fun SeatTitle(seat: ReservedSeat) {
    Column {
        Text(
            text = seat.seatName,
            style = MaterialTheme.typography.subtitle1.copy(
                color = Color.Black,
                fontWeight = FontWeight(500)
            )
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = seat.bookingDay,
                style = MaterialTheme.typography.subtitle1.copy(
                    color = textGrayColor,
                    fontWeight = FontWeight(400)
                )
            )
            Text(
                text = seat.bookingTime,
                style = MaterialTheme.typography.subtitle1.copy(
                    color = textGrayColor,
                    fontWeight = FontWeight(400)
                )
            )
        }
    }
}