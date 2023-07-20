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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.BookingRoomComponent
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.BookingRoomView
import band.effective.office.tablet.ui.mainScreen.mockComponets.MockSettingView
import band.effective.office.tablet.ui.mainScreen.mockComponets.MockSettingsComponent
import band.effective.office.tablet.ui.mainScreen.roomInfoComponents.RoomInfoComponent
import band.effective.office.tablet.ui.selectRoomScreen.FreeSelectRoomView
import band.effective.office.tablet.ui.selectRoomScreen.RealFreeSelectRoomComponent
import band.effective.office.tablet.ui.selectRoomScreen.SelectRoomComponent
import band.effective.office.tablet.ui.selectRoomScreen.SelectRoomView
import band.effective.office.tablet.ui.selectRoomScreen.SelectRoomComponentImpl
import band.effective.office.tablet.ui.selectRoomScreen.SelectRoomScreen

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
fun MainScreenView(
    room: RoomInfo,
    showBookingModal: Boolean,
    showFreeRoomModal: Boolean,
    mockComponent: MockSettingsComponent,
    bookingRoomComponent: BookingRoomComponent,
    selectRoomComponent: SelectRoomComponent,
    onOpenFreeModalRequest: () -> Unit,
    onCloseFreeModalRequest: () -> Unit,
    onFreeRoomRequest: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        /*NOTE(Maksim Mishenko):
    * infoViewWidth is part of the width occupied by roomInfoView
    * infoViewWidth = infoViewFrame.width / mainScreenFrame.width
    * where infoViewFrame, mainScreenFrame is frames from figma and all width I get from figma*/
        val infoViewWidth = 627f / 1133f
        Row(modifier = Modifier.fillMaxSize()) {
            RoomInfoComponent(
                modifier = Modifier.fillMaxHeight().fillMaxWidth(infoViewWidth),
                room = room,
                onOpenModalRequest = { onOpenFreeModalRequest() }
            )
            Box(modifier = Modifier.fillMaxSize()) {
                BookingRoomView(
                    modifier = Modifier.fillMaxSize()
                        .padding(25.dp),
                    bookingRoomComponent = bookingRoomComponent
                )
                MockSettingView(mockComponent)
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                showBookingModal -> SelectRoomScreen(component = selectRoomComponent)
                showFreeRoomModal -> FreeSelectRoomView(
                    onCloseRequest = { onCloseFreeModalRequest() },
                    onFreeRoomRequest = { onFreeRoomRequest() })
            }
        }
    }
}
