package band.effective.office.tablet.ui.mainScreen

import band.effective.office.tablet.getPlatformName
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainComponent(
    componentContext: ComponentContext,
    private val onClick: () -> Unit
) : ComponentContext by componentContext {
    private var mutableState = MutableStateFlow(MainScreenState.defaultState)
    val state = mutableState.asStateFlow()

    fun sendEvent(event: MainScreenEvent) =
        when (event) {
            is MainScreenEvent.OnCLick -> {
                onClick()
            }

            is MainScreenEvent.OnDoubleTub -> {}
        }
}