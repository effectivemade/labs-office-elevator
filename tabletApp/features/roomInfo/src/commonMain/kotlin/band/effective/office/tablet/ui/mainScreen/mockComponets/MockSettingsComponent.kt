package band.effective.office.tablet.ui.mainScreen.mockComponets

import kotlinx.coroutines.flow.StateFlow

interface MockSettingsComponent {
    val state: StateFlow<MockState>
    fun sendEvent(event: MockSettingsEvent)
}