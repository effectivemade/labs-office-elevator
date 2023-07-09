package band.effective.office.tablet.ui.mainScreen

import band.effective.office.tablet.ui.mainScreen.components.mockComponets.MockSettingsComponent
import kotlinx.coroutines.flow.StateFlow

interface MainComponent {
    val state: StateFlow<MainScreenState>
    val mockSettingsComponent: MockSettingsComponent
    fun sendEvent(event: MainScreenEvent)
}