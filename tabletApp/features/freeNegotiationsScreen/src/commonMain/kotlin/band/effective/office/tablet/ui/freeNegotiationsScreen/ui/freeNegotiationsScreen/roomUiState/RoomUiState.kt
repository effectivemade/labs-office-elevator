package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.roomUiState

import band.effective.office.tablet.domain.model.RoomInfo

data class RoomInfoUiState(
    val room: RoomInfo,
    val changeEventTime: Int,
    val state: RoomState
)

enum class RoomState(val codeState: Int) {
    FREE(0),
    SOON_BUSY(1),
    BUSY(2);
}