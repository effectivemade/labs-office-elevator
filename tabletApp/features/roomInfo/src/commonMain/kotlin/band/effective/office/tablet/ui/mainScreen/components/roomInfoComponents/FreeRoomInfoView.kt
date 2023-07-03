package band.effective.office.tablet.ui.mainScreen.components.roomInfoComponents

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import band.effective.office.tablet.domain.model.EventInfo

@Composable
fun FreeRoomInfoView(
    name: String,
    capacity: Int,
    isHaveTv: Boolean,
    electricSocketCount: Int,
    nextEvent: EventInfo?
) {
    Text(text = "FreeRoomInfoView")
}