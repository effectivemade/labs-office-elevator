package band.effective.office.elevator.ui.main.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ui.models.ReservedSeat

@Composable
fun BookingInformation(
    reservedSeats: List<ReservedSeat>,
    onClickBook: () -> Unit,
    onClickOptionMenu: (Int) -> Unit,
    onClickShowOptions: () -> Unit,
    onClickOpenCalendar: () -> Unit,
    onClickOpenBottomDialog: () -> Unit,
    showOptionsMenu: Boolean,
    onClickCloseOptionMenu: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        DateSelection(onClickOpenCalendar = onClickOpenCalendar, onClickOpenBottomDialog = onClickOpenBottomDialog)
        Spacer(modifier = Modifier.height(24.dp))
        SeatsReservation(
            reservedSeats = reservedSeats,
            onClickBook = onClickBook,
            onClickOptionMenu = onClickOptionMenu,
            onClickShowOptions = onClickShowOptions,
            showOptionsMenu = showOptionsMenu,
            onClickCloseOptionMenu = onClickCloseOptionMenu
        )
    }
}