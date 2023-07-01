package band.effective.office.tablet.ui.mainScreen

import band.effective.office.tablet.ui.mainScreen.MainScreenEvent
import band.effective.office.tablet.ui.mainScreen.MainScreenState
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

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