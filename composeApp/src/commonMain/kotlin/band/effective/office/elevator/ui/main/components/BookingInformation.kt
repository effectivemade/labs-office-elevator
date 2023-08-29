package band.effective.office.elevator.ui.main.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ui.models.ReservedSeat
import kotlinx.datetime.LocalDate

@Composable
fun BookingInformation(
    reservedSeats: List<ReservedSeat>,
    currentDate: LocalDate,
    dateFiltrationOnReserves: Boolean,
    onClickBook: () -> Unit,
    onClickOptionMenu: (String) -> Unit,
    onClickOpenCalendar: () -> Unit,
    onClickOpenBottomDialog: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        DateSelection(
            onClickOpenCalendar = onClickOpenCalendar,
            onClickOpenBottomDialog = onClickOpenBottomDialog,
            currentDate = currentDate,
            dateFiltration = dateFiltrationOnReserves
        )
        Spacer(modifier = Modifier.height(24.dp))
        SeatsReservation(
            reservedSeats = reservedSeats,
            onClickBook = onClickBook,
            onClickOptionMenu = onClickOptionMenu

        )
    }
}