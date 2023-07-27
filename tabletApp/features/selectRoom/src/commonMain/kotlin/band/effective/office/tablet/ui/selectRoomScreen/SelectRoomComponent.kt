package band.effective.office.tablet.ui.selectRoomScreen

import kotlinx.coroutines.flow.StateFlow
import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.ui.selectRoomScreen.store.SelectRoomStore

interface SelectRoomComponent {

    val state: StateFlow<SelectRoomStore.State>
    fun onIntent(intent: SelectRoomStore.Intent)
}