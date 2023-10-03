package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.roomUiState

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo

data class RoomInfoUiState(
    val room: RoomInfo,
    val changeEventTime: Int,
    val state: RoomState
) {
    companion object {
        val defaultValue =
            RoomInfoUiState(
                room = RoomInfo.defaultValue,
                state = RoomState.FREE,
                changeEventTime = 0
            )
    }
}

sealed class RoomState(val codeState: Int, open var event: EventInfo? = null) {
    object FREE : RoomState(0)
    data class SOON_BUSY(override var event: EventInfo? = null): RoomState(1,event)
    data class BUSY(override var event: EventInfo? = null): RoomState(1,event)
}

//enum class RoomState(val codeState: Int, var event: EventInfo? = null) {
//    FREE(0),
//    SOON_BUSY(1),
//    BUSY(2);
//}