package band.effective.office.tablet.ui.mainScreen.components.mockComponets

import band.effective.office.tablet.domain.MockController
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RealMockSettingsComponent(
    componentContext: ComponentContext,
    private val updateData: () -> Unit
) : ComponentContext by componentContext, KoinComponent, MockSettingsComponent {
    private var mutableState = MutableStateFlow(MockState())
    override val state = mutableState.asStateFlow()

    private val mockController: MockController by inject()

    init {
        reloadData()
    }

    /**Synchronize data in mock controller and in state*/
    private fun reloadData() {
        /*mutableState.update {
            it.copy(
                isBusy = mockController.isBusy,
                isManyEvent = mockController.isManyEvent,
                isHaveTv = mockController.isHaveTV,
                isBusyTime = mockController.isBusyTime
            )
        }*/
    }

    override fun sendEvent(event: MockSettingsEvent) {
        /*when (event) {
            is MockSettingsEvent.OnSwitchBusy -> mockController.isBusy = event.newState
            is MockSettingsEvent.OnSwitchEventCount -> mockController.isManyEvent = event.newState
            is MockSettingsEvent.OnSwitchTv -> mockController.isHaveTV = event.newState
            is MockSettingsEvent.OnSwitchBusyTime -> mockController.isBusyTime = event.newState
            is MockSettingsEvent.OnSwitchVisible -> mutableState.update { it.copy(isVisible = !it.isVisible) }
        }
        reloadData()
        updateData()*/
    }

}