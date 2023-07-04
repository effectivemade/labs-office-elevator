package band.effective.office.tablet.ui.mainScreen.components.bookingRoomComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.ui.mainScreen.components.bookingRoomComponents.uiComponents.EventLengthView

@Composable
fun BookingRoomView(modifier: Modifier = Modifier, bookingRoomComponent: BookingRoomComponent) {
    val state by bookingRoomComponent.state.collectAsState()
    Column(modifier = modifier) {
        DateTimeView(component = bookingRoomComponent.dateTimeComponent)
        EventLengthView(
            modifier = Modifier.fillMaxWidth().height(100.dp).padding(25.dp),
            component = bookingRoomComponent.eventLengthComponent,
            currentLength = state.length
        )
        EventOrganizerView(component = bookingRoomComponent.eventOrganizerComponent)
        Button(onClick = { bookingRoomComponent.sendEvent(BookingRoomViewEvent.OnBookingOtherOtherRoom) }) {
            Text(text = "SelectOtherRoom")
        }
    }
}

@Composable
fun DateTimeView(component: RealDateTimeComponent) {

}

@Composable
fun EventOrganizerView(component: RealEventOrganizerComponent) {
}