package band.effective.office.tablet.ui.mainScreen.mainScreen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.ui.mainScreen.mainScreen.uiComponents.Disconnect
import band.effective.office.tablet.ui.mainScreen.roomInfoComponents.RoomInfoComponent
import band.effective.office.tablet.ui.mainScreen.slotComponent.SlotComponent
import band.effective.office.tablet.ui.mainScreen.slotComponent.SlotList

@SuppressLint("NewApi", "StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
fun MainScreenView(
    roomInfoComponent: RoomInfoComponent,
    slotComponent: SlotComponent,
    isDisconnect: Boolean,
    onEventUpdateRequest: (EventInfo) -> Unit,
    onSettings: () -> Unit
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
                roomInfoComponent = roomInfoComponent,
                onEventUpdateRequest = onEventUpdateRequest,
                onSettings = onSettings
            )
            Box(modifier = Modifier.fillMaxSize()) {
                SlotList(slotComponent)
                Box() {
                    Disconnect(visible = isDisconnect)
                }
            }
        }
    }
}
