package band.effective.office.elevator.ui.main_screem_content.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.EffectiveButton
import band.effective.office.elevator.text_color_tertiary
import band.effective.office.elevator.ui.models.ReservedSeat

@Composable
fun SeatsReservation(
    reservedSeats: List<ReservedSeat>,
    onClickBook: () -> Unit,
    onClickShowMap: () -> Unit,
    onClickShowOptions: () -> Unit
) {
    when(reservedSeats.isEmpty()) {
        true -> EmptyReservation(onClickBook)
        false -> NonEmptyReservation(
            reservedSeats = reservedSeats,
            onClickShowMap = onClickShowMap,
            onClickShowOptions = onClickShowOptions
        )
    }
}

@Composable
fun EmptyReservation(onClickBook: () -> Unit) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = MainRes.string.none_booking_seat,
            fontSize = 15.sp,
            color = text_color_tertiary
        )
        Spacer(modifier = Modifier.height(16.dp))
        EffectiveButton(
            buttonText = MainRes.string.book_a_seat,
            onClick = onClickBook,
            modifier = Modifier.width(280.dp)
        )
    }
}

@Composable
fun NonEmptyReservation(
    reservedSeats: List<ReservedSeat>,
    onClickShowMap: () -> Unit,
    onClickShowOptions: () -> Unit,
) {
    LazyColumn (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        items(reservedSeats) { seat ->
            BookingCard(
                seat = seat,
                onClickShowMap = onClickShowMap,
                onClickShowOptions = onClickShowOptions
            )
        }
    }
}