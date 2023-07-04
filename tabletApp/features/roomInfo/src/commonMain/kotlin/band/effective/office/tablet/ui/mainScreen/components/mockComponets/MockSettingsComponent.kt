package band.effective.office.tablet.ui.mainScreen.components.mockComponets

import kotlinx.coroutines.flow.StateFlow

interface MockSettingsComponent {
    val state: StateFlow<MockState>
    fun sendEvent(event: MockSettingsEvent)
}