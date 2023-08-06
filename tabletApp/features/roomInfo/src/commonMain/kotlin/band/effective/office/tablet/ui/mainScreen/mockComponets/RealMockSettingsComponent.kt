package band.effective.office.tablet.ui.mainScreen.mockComponets

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent

class RealMockSettingsComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext, KoinComponent, MockSettingsComponent {

    private var mutableState = MutableStateFlow(MockState())
    override val state = mutableState.asStateFlow()
    override fun sendEvent(event: MockSettingsEvent) {
        TODO("Not yet implemented")
    }
    /*

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
               is MockSettingsEvent.OnSwitchSuccess -> {
                   mockController.changeSuccess(event.newState)
                   mutableState.update { it.copy(isSuccess = event.newState) }
               }
           }
       }*/

}