package band.effective.office.tablet.ui.mainScreen.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.ui.mainScreen.components.bookingRoomComponents.BookingRoomComponent
import band.effective.office.tablet.ui.mainScreen.components.bookingRoomComponents.BookingRoomView
import band.effective.office.tablet.ui.mainScreen.components.mockComponets.MockSettingView
import band.effective.office.tablet.ui.mainScreen.components.mockComponets.MockSettingsComponent
import band.effective.office.tablet.ui.mainScreen.components.roomInfoComponents.RoomInfoComponent

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
fun MainScreenView(
    room: RoomInfo,
    mockComponent: MockSettingsComponent,
    bookingRoomComponent: BookingRoomComponent
) {
    /*NOTE(Maksim Mishenko):
    * infoViewWidth is part of the width occupied by roomInfoView
    * infoViewWidth = infoViewFrame.width / mainScreenFrame.width
    * where infoViewFrame, mainScreenFrame is frames from figma and all width I get from figma*/
    val infoViewWidth = 627f / 1133f
    Row(modifier = Modifier.fillMaxSize().background(color = Color(0xff1E1C1A))) {
        RoomInfoComponent(
            modifier = Modifier.fillMaxHeight().fillMaxWidth(infoViewWidth),
            room = room
        )
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            BookingRoomView(
                modifier = Modifier.fillMaxSize().padding(25.dp),
                bookingRoomComponent = bookingRoomComponent
            )
            //MockSettingView(mockComponent)
        }
    }
}