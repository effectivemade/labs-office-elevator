package band.effective.office.elevator.ui.main_screem_content.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButtonDefaults
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
fun BookingCard(
    seat: ReservedSeat,
    onClickShowMap: () -> Unit,
    onClickShowOptions: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .fillMaxWidth()
    ) {
        Row {
            SeatIcon()
            Spacer(modifier = Modifier.width(12.dp))
            SeatTitle(seat)
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = onClickShowMap,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth(0.7f),
                border = BorderStroke(1.dp, MaterialTheme.colors.primary),
                elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = MaterialTheme.colors.primary
                )
            ){
                Text(
                    text = MainRes.string.show_map,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = onClickShowOptions,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp)),
                border = BorderStroke(1.dp, MaterialTheme.colors.primary),
                elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = MaterialTheme.colors.primary
                )
            ){
                Image(
                    painter = painterResource(MainRes.image.mi_options_vertical),
                    contentDescription = null,
//                    modifier = Modifier
//                        .padding(all = 8.dp)
//                        .size(20.dp)
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
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = seat.bookingDay,
                fontSize = 15.sp
            )
            Divider(
                modifier = Modifier
                .width(19.dp)
                .rotate(90f),
                thickness = 2.dp
            )
//            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = seat.bookingTime,
                fontSize = 15.sp
            )
        }
    }
}