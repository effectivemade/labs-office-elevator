package band.effective.office.tablet.ui.selectRoomScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.features.selectRoom.MainRes
import band.effective.office.tablet.ui.selectRoomScreen.successBooking.SuccessSelectRoomView
import band.effective.office.tablet.ui.selectRoomScreen.uiComponents.BookingButtonView
import band.effective.office.tablet.ui.selectRoomScreen.uiComponents.CrossButtonView
import band.effective.office.tablet.ui.selectRoomScreen.uiComponents.DateTimeView
import band.effective.office.tablet.ui.selectRoomScreen.uiComponents.LengthEventView
import band.effective.office.tablet.ui.selectRoomScreen.uiComponents.OrganizerEventView
import band.effective.office.tablet.ui.selectRoomScreen.uiComponents.Title
import band.effective.office.tablet.ui.selectRoomScreen.uiComponents.TitleFieldView
import band.effective.office.tablet.ui.theme.CustomDarkColors
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.utils.time24

@Composable
fun SelectRoomView(
    booking: Booking,
    close: () -> Unit,
    bookRoom: () -> Unit
) {
    val shape = RoundedCornerShape(24)
    val modifier = Modifier
        .clip(shape)
        .background(LocalCustomColorsPalette.current.mountainBackground)

    Box(
        modifier = Modifier
            .size(575.dp, 525.dp)
            .clip(RoundedCornerShape(3))
            .background(LocalCustomColorsPalette.current.elevationBackground),
    ) {
        Column(
            modifier = Modifier
                .matchParentSize()
                .padding(top = 35.dp, bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CrossButtonView(
                Modifier.width(575.dp),
                onDismissRequest = { close() }
            )
            Title(
                Modifier.width(415.dp),
                booking
            )
            Spacer(modifier = Modifier.height(16.dp))
            TitleFieldView(
                modifier = Modifier.width(415.dp),
                title = MainRes.string.whenEvent
            )
            Spacer(modifier = Modifier.height(10.dp))
            DateTimeView(
                modifier = modifier.height(64.dp).width(415.dp),
                booking = booking
            )
            Spacer(modifier = Modifier.height(16.dp))
            RowInfoLengthAndOrganizer(modifier, booking)
            Spacer(modifier = Modifier.height(40.dp))
            BookingButtonView(
                modifier = Modifier.height(64.dp).width(415.dp),
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
}

@Composable
fun RowInfoLengthAndOrganizer(
    modifier: Modifier,
    booking: Booking
) {
    Row {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            TitleFieldView(
                modifier = Modifier.width(156.dp),
                title = MainRes.string.how_much
            )
            LengthEventView(
                modifier = modifier.height(64.dp).width(156.dp),
                booking = booking
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            TitleFieldView(
                modifier = Modifier.width(243.dp),
                title = MainRes.string.organizer
            )
            OrganizerEventView(
                modifier = modifier.height(64.dp).width(243.dp),
                booking = booking
            )
        }
    }
}