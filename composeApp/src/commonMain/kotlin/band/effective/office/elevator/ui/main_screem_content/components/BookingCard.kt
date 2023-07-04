package band.effective.office.elevator.ui.main_screem_content.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.ui.models.ReservedSeat
import io.github.skeptick.libres.compose.painterResource

@Composable
fun BookingCard(seat: ReservedSeat, onClickShowMap: () -> Unit) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Row {
            SeatIcon()
            Spacer(modifier = Modifier.width(12.dp))
            SeatTitle(seat)
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row {
            Button(
                onClick = onClickShowMap,
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(size = 8.dp)
                    ),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = MaterialTheme.colors.primary
                )
            ){
                Text(
                    text = MainRes.string.show_map,
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = onClickShowMap,
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(size = 8.dp)
                    ),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = MaterialTheme.colors.primary
                )
            ){
                Image(
                    painter = painterResource(MainRes.image.mi_options_vertical),
                    contentDescription = null,
                    modifier = Modifier.padding(all = 8.dp)
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
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            Text(
                text = seat.bookingDay,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Divider(modifier = Modifier.rotate(90f))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = seat.bookingTime,
                fontSize = 15.sp
            )
        }
    }
}