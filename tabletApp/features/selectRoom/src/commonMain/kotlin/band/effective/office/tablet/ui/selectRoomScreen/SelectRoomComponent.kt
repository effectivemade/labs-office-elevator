package band.effective.office.tablet.ui.selectRoomScreen

import kotlinx.coroutines.flow.StateFlow
import band.effective.office.tablet.domain.model.Booking

interface SelectRoomComponent {
    val state: StateFlow<SelectRoomScreenState>
    fun bookRoom()
}