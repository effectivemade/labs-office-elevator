package band.effective.office.elevator.ui.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ui.models.ReservedSeat

@Composable
fun BookingInformation(
    reservedSeats: List<ReservedSeat>,
    onClickBook: () -> Unit,
    onClickShowMap: () -> Unit,
    onClickShowOptions: () -> Unit,
    onClickOpenCalendar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        DateSelection(onClickOpenCalendar = onClickOpenCalendar)
        Spacer(modifier = Modifier.height(24.dp))
        SeatsReservation(
            reservedSeats = reservedSeats,
            onClickBook = onClickBook,
            onClickShowMap = onClickShowMap,
            onClickShowOptions = onClickShowOptions
        )
    }
}