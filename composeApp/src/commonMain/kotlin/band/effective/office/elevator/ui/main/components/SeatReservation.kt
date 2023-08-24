package band.effective.office.elevator.ui.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.DropDownMenu
import band.effective.office.elevator.components.EffectiveButton
import band.effective.office.elevator.textGrayColor
import band.effective.office.elevator.ui.booking.components.modals.BookingContextMenu
import band.effective.office.elevator.ui.models.ReservedSeat
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SeatsReservation(
    reservedSeats: List<ReservedSeat>,
    onClickBook: () -> Unit,
    onClickOptionMenu: (String) -> Unit,
) {
    when(reservedSeats.isEmpty()) {
        true -> EmptyReservation(onClickBook)
        false -> NonEmptyReservation(
            reservedSeats = reservedSeats,
            onClickOptionMenu = onClickOptionMenu
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
            text = stringResource(MainRes.strings.none_booking_seat),
            fontSize = 15.sp,
            color = textGrayColor
        )
        Spacer(modifier = Modifier.height(16.dp))
        EffectiveButton(
            buttonText = stringResource(MainRes.strings.book_a_seat),
            onClick = onClickBook,
            modifier = Modifier.width(280.dp),
        )
    }
}

@Composable
fun NonEmptyReservation(
    reservedSeats: List<ReservedSeat>,
    onClickOptionMenu: (String) -> Unit
) {
    LazyColumn (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        items(reservedSeats) { seat ->
            BookingCard(
                seat = seat,
                onClickOptionMenu = onClickOptionMenu,
            )

        }
    }

}