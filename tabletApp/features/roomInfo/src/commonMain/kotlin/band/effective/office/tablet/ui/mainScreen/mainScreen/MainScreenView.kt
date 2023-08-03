package band.effective.office.tablet.ui.mainScreen.mainScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.ui.freeSelectRoom.FreeSelectRoomComponent
import band.effective.office.tablet.ui.freeSelectRoom.FreeSelectRoomView
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.BookingRoomComponent
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.BookingRoomView
import band.effective.office.tablet.ui.mainScreen.mainScreen.uiComponents.Disconnect
import band.effective.office.tablet.ui.mainScreen.mockComponets.MockSettingView
import band.effective.office.tablet.ui.mainScreen.mockComponets.MockSettingsComponent
import band.effective.office.tablet.ui.mainScreen.roomInfoComponents.RoomInfoComponent
import band.effective.office.tablet.ui.selectRoomScreen.SelectRoomComponent
import band.effective.office.tablet.ui.selectRoomScreen.SelectRoomScreen

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
fun MainScreenView(
    showBookingModal: Boolean,
    showFreeRoomModal: Boolean,
    mockComponent: MockSettingsComponent,
    bookingRoomComponent: BookingRoomComponent,
    selectRoomComponent: SelectRoomComponent,
    freeSelectRoomComponent: FreeSelectRoomComponent,
    roomInfoComponent: RoomInfoComponent,
    showModal: Boolean,
    isDisconnect: Boolean
) {
    Box(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colors.background)) {
        /*NOTE(Maksim Mishenko):
    * infoViewWidth is part of the width occupied by roomInfoView
    * infoViewWidth = infoViewFrame.width / mainScreenFrame.width
    * where infoViewFrame, mainScreenFrame is frames from figma and all width I get from figma*/
        val infoViewWidth = 627f / 1133f
        Row(modifier = Modifier.fillMaxSize()) {
            RoomInfoComponent(
                modifier = Modifier.fillMaxHeight().fillMaxWidth(infoViewWidth),
                roomInfoComponent = roomInfoComponent
            )
            Box(modifier = Modifier.fillMaxSize()) {
                BookingRoomView(
                    modifier = Modifier.background(color = MaterialTheme.colors.surface)
                        .fillMaxSize()
                        .padding(25.dp),
                    bookingRoomComponent = bookingRoomComponent
                )
                Box() {
                    MockSettingView(mockComponent)
                    Disconnect(visible = isDisconnect)
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize()
                .background(color = if (showModal) Color.Black.copy(alpha = 0.9f) else Color.Transparent)
        ) {
            when {
                showBookingModal -> SelectRoomScreen(component = selectRoomComponent)
                showFreeRoomModal -> FreeSelectRoomView(freeSelectRoomComponent = freeSelectRoomComponent)
            }
        }
    }
}
