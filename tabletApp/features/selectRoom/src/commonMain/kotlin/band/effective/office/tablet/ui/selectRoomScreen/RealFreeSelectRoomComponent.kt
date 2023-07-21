package band.effective.office.tablet.ui.selectRoomScreen

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent

class RealFreeSelectRoomComponent(
    componentContext: ComponentContext,
    private val onCloseRequest: () -> Unit
) : ComponentContext by componentContext, FreeSelectRoomComponent, KoinComponent  {

    private var _state = MutableStateFlow(FreeSelectRoomState.defaultState)
    override val state = _state.asStateFlow()

    override fun onButtonClicked() {
        _state.value = _state.value.copy(isPressed = !_state.value.isPressed)
    }

    fun close() {
        onCloseRequest()
    }
}