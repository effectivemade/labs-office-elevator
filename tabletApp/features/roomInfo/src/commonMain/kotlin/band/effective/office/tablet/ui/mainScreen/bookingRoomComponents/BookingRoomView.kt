package band.effective.office.tablet.ui.mainScreen.bookingRoomComponents

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.store.BookingStore
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.uiComponents.Alert
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.uiComponents.DateTimeView
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.uiComponents.EventLengthView
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.uiComponents.EventOrganizerView

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
fun BookingRoomView(modifier: Modifier = Modifier, bookingRoomComponent: BookingRoomComponent) {
    val state by bookingRoomComponent.state.collectAsState()
    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colors.surface) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(63.dp))
            Text(
                text = MainRes.string.booking_view_title,
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(25.dp))
            DateTimeView(
                modifier = Modifier.fillMaxWidth().height(100.dp),
                selectDate = if (state.isSelectCurrentTime) state.currentDate else state.selectDate,
                increment = {bookingRoomComponent.sendIntent(BookingStore.Intent.OnChangeDate(1))},
                decrement = {bookingRoomComponent.sendIntent(BookingStore.Intent.OnChangeDate(-1))}
            )
            Spacer(modifier = Modifier.height(25.dp))
            EventLengthView(
                modifier = Modifier.fillMaxWidth().height(100.dp),
                currentLength = state.length,
                increment = {bookingRoomComponent.sendIntent(BookingStore.Intent.OnChangeLength(30))},
                decrement = {bookingRoomComponent.sendIntent(BookingStore.Intent.OnChangeLength(-15))}
            )
            if (state.isBusy) {
                Spacer(Modifier.height(10.dp))
                Alert(modifier = Modifier.fillMaxWidth(), text = MainRes.string.no_correct_time)
            }
            Spacer(modifier = Modifier.height(25.dp))
            EventOrganizerView(
                modifier = Modifier.fillMaxWidth().height(100.dp),
                organizers = state.organizers,
                expanded = state.isExpandedOrganizersList,
                selectedItem = state.organizer,
                onExpandedChange = {bookingRoomComponent.sendIntent(BookingStore.Intent.OnChangeExpanded)},
                onSelectItem = {bookingRoomComponent.sendIntent(BookingStore.Intent.OnChangeOrganizer(it))}
            )
            if (state.isOrganizerError) {
                Spacer(Modifier.height(10.dp))
                Alert(
                    modifier = Modifier.fillMaxWidth(),
                    text = MainRes.string.no_select_organizer_alert
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            if (state.isBusy) {
                Button(
                    modifier = Modifier.fillMaxWidth().height(60.dp)
                        .clip(RoundedCornerShape(100.dp)).border(
                            width = 3.dp,
                            shape = RoundedCornerShape(100.dp),
                            color = MaterialTheme.colors.onPrimary
                        ),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    onClick = { bookingRoomComponent.sendIntent(BookingStore.Intent.OnBookingOtherRoom) }
                ) {
                    Text(text = MainRes.string.see_free_room)
                }
                Spacer(Modifier.height(10.dp))
            }
            Button(
                modifier = Modifier.fillMaxWidth().height(60.dp).clip(RoundedCornerShape(100.dp)).focusable(true),
                onClick = { bookingRoomComponent.sendIntent(BookingStore.Intent.OnBookingCurrentRoom) }
            ) {
                Text(text = MainRes.string.booking_button_text.format(roomName = state.roomName))
            }
        }
    }
}