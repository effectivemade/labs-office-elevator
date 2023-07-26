package band.effective.office.tablet.ui.selectRoomScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.features.selectRoom.MainRes
import band.effective.office.tablet.ui.selectRoomScreen.uiComponents.BookingButtonView
import band.effective.office.tablet.ui.selectRoomScreen.uiComponents.CrossButtonView
import band.effective.office.tablet.ui.selectRoomScreen.uiComponents.DateTimeView
import band.effective.office.tablet.ui.selectRoomScreen.uiComponents.DurationEventView
import band.effective.office.tablet.ui.selectRoomScreen.uiComponents.OrganizerEventView
import band.effective.office.tablet.ui.selectRoomScreen.uiComponents.Title
import band.effective.office.tablet.ui.selectRoomScreen.uiComponents.TitleFieldView
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.utils.time24

@Composable
fun SelectRoomView(
    booking: Booking,
    close: () -> Unit,
    bookRoom: () -> Unit
) {

    val modifier = Modifier
        .fillMaxWidth()
        .padding(start = 85.dp)

    Column(
        modifier = Modifier
            .fillMaxWidth(0.4f)
            .fillMaxHeight(0.7f)
            .clip(RoundedCornerShape(3))
            .background(LocalCustomColorsPalette.current.elevationBackground)
            .padding(top = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CrossButtonView(
            Modifier
                .fillMaxWidth()
                .padding(end = 42.dp),
            onDismissRequest = { close() }
        )
        Title(
            Modifier.fillMaxWidth(),
            booking
        )
        Spacer(modifier = Modifier.height(35.dp))
        TitleFieldView(
            modifier = modifier,
            title = MainRes.string.whenEvent
        )
        Spacer(modifier = Modifier.height(10.dp))
        DateTimeView(
            modifier = modifier,
            booking = booking
        )
        Spacer(modifier = Modifier.height(20.dp))
        TitleFieldView(
            modifier = modifier,
            title = MainRes.string.how_much
        )
        Spacer(modifier = Modifier.height(10.dp))
        DurationEventView(
            modifier = modifier,
            booking = booking
        )
        Spacer(modifier = Modifier.height(20.dp))
        TitleFieldView(
            modifier = modifier,
            title = MainRes.string.organizer
        )
        Spacer(modifier = Modifier.height(10.dp))
        OrganizerEventView(
            modifier = modifier,
            booking = booking
        )
        Spacer(modifier = Modifier.height(40.dp))
        BookingButtonView(
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .fillMaxHeight(0.45f),
            shape = RoundedCornerShape(100),
            text = MainRes.string.booking_time_button.format(
                startTime = booking.eventInfo.startTime.time24(),
                finishTime = booking.eventInfo.finishTime.time24()
            ),
            onClick = {
                bookRoom()
            }
        )
    }
}