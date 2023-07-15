package band.effective.office.tablet.ui.selectRoomScreen.successBooking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import band.effective.office.tablet.features.selectRoom.MainRes
import band.effective.office.tablet.ui.selectRoomScreen.RowInfoLengthAndOrganizer
import band.effective.office.tablet.ui.selectRoomScreen.SelectRoomComponentImpl
import band.effective.office.tablet.ui.selectRoomScreen.SelectRoomScreenState
import band.effective.office.tablet.ui.selectRoomScreen.successBooking.uiComponents.IconSuccess
import band.effective.office.tablet.ui.selectRoomScreen.successBooking.uiComponents.SuccessText
import band.effective.office.tablet.ui.selectRoomScreen.uiComponents.BookingButtonView
import band.effective.office.tablet.ui.selectRoomScreen.uiComponents.CrossButtonView
import band.effective.office.tablet.ui.selectRoomScreen.successBooking.uiComponents.DateTimeView
import band.effective.office.tablet.ui.selectRoomScreen.successBooking.uiComponents.OrganizerEventView
import band.effective.office.tablet.ui.selectRoomScreen.uiComponents.Title
import band.effective.office.tablet.ui.selectRoomScreen.uiComponents.TitleFieldView
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.utils.time24

@Composable
fun SuccessSelectRoomView(
    component: SelectRoomComponentImpl
) {
   // Dialog(
     //   onDismissRequest = { component.close() }
   // )
   // {
        Box(
            modifier = Modifier
                .size(551.dp, 510.dp)
                .clip(RoundedCornerShape(5))
                .background(LocalCustomColorsPalette.current.elevationBackground),
        ) {
            Column(
                modifier = Modifier.matchParentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(30.dp))
                CrossButtonView(
                    Modifier.width(551.dp),
                    onDismissRequest = { component.close() }
                )
                Spacer(modifier = Modifier.height(2.dp))
                IconSuccess()
                Spacer(modifier = Modifier.height(24.dp))
                SuccessText(modifier = Modifier.width(227.dp), nameRoom = component.booking.nameRoom)
                Spacer(modifier = Modifier.height(60.dp))
                DateTimeView(modifier = Modifier.width(391.dp), booking = component.booking)
                Spacer(modifier = Modifier.height(20.dp))
                OrganizerEventView(booking = component.booking)
                Spacer(modifier = Modifier.height(60.dp))
                BookingButtonView(
                    modifier = Modifier.height(64.dp).width(415.dp),
                    shape = RoundedCornerShape(40),
                    text = MainRes.string.on_main,
                    onClick = {
                        component.close()
                    }
                )
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
  //  }
}