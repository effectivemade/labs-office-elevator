package band.effective.office.elevator.ui.main_screem_content.components

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
    onClickBook: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.onBackground)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        DateSelection()
        Spacer(modifier = Modifier.height(24.dp))
        SeatsReservation(reservedSeats = reservedSeats, onClickBook = onClickBook)
    }
}