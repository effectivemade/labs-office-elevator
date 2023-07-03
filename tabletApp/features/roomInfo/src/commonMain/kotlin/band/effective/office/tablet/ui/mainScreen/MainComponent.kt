package band.effective.office.tablet.ui.mainScreen

import band.effective.office.tablet.domain.RoomInteractor
import band.effective.office.tablet.domain.RoomInteractorImpl
import band.effective.office.tablet.network.MockRoomInfoRepository
import band.effective.office.tablet.ui.mainScreen.MainScreenEvent
import band.effective.office.tablet.ui.mainScreen.MainScreenState
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainComponent(
    componentContext: ComponentContext,
    private val onClick: () -> Unit,
) : ComponentContext by componentContext, KoinComponent {

    private val interactor: RoomInteractor by inject()

    private var mutableState = MutableStateFlow(MainScreenState.defaultState)
    val state = mutableState.asStateFlow()

    init {
        mutableState.update { it.copy(isLoad = false, isData = true, roomInfo = interactor.getRoomInfo("sirius")) }
    }

    fun sendEvent(event: MainScreenEvent) =
        when (event) {
            is MainScreenEvent.OnCLick -> {
                onClick()
            }

            is MainScreenEvent.OnDoubleTub -> {}
        }
}