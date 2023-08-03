package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen

import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.store.FreeNegotiationsStore
import band.effective.office.tablet.ui.selectRoomScreen.SelectRoomComponentImpl
import kotlinx.coroutines.flow.StateFlow

interface FreeNegotiationsComponent {
    val state: StateFlow<FreeNegotiationsStore.State>
    val selectRoomComponent: SelectRoomComponentImpl
    fun onIntent(intent: FreeNegotiationsStore.Intent)
}