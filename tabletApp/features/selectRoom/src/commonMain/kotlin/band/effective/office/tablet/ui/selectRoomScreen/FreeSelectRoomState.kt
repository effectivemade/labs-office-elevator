package band.effective.office.tablet.ui.selectRoomScreen

data class FreeSelectRoomState(
    var isPressed: Boolean
) {
    companion object {
        val defaultState = FreeSelectRoomState(
            isPressed = false
        )
    }
}