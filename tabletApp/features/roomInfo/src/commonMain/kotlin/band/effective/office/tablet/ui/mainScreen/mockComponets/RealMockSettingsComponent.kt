package band.effective.office.tablet.ui.mainScreen.mockComponets

import band.effective.office.tablet.domain.MockController
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RealMockSettingsComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext, KoinComponent, MockSettingsComponent {
    private var mutableState = MutableStateFlow(MockState())
    override val state = mutableState.asStateFlow()

    private val mockController: MockController by inject()

    override fun sendEvent(event: MockSettingsEvent) {
        when (event) {
            is MockSettingsEvent.OnSwitchBusy -> {
                mockController.changeBusy(event.newState)
                mutableState.update { it.copy(isBusy = event.newState) }
            }

            is MockSettingsEvent.OnSwitchEventCount -> {
                mockController.changeEventCount(event.newState)
                mutableState.update { it.copy(isManyEvent = event.newState) }
            }

            is MockSettingsEvent.OnSwitchTv -> {
                mockController.changeHaveTv(event.newState)
                mutableState.update { it.copy(isHaveTv = event.newState) }
            }

            is MockSettingsEvent.OnSwitchVisible -> mutableState.update { it.copy(isVisible = !it.isVisible) }
        }
    }

}