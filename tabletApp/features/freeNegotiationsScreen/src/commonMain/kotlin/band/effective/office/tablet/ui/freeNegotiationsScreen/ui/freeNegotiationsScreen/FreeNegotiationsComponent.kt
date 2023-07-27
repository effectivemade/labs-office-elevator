package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen

import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.store.FreeNegotiationsStore
import kotlinx.coroutines.flow.StateFlow

interface FreeNegotiationsComponent {
    val state: StateFlow<FreeNegotiationsStore.State>
    fun onIntent(intent: FreeNegotiationsStore.Intent)
}