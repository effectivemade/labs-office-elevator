package band.effective.office.tablet.ui.selectRoomScreen

import kotlinx.coroutines.flow.StateFlow

interface FreeSelectRoomComponent {
    val state: StateFlow<FreeSelectRoomState>

    fun onButtonClicked()
}