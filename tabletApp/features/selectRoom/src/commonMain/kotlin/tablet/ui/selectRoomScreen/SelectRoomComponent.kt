package tablet.ui.selectRoomScreen

import kotlinx.coroutines.flow.StateFlow
import tablet.domain.model.Booking

interface SelectRoomComponent {
    val state: StateFlow<SelectRoomScreenState>
    fun bookRoom()
}