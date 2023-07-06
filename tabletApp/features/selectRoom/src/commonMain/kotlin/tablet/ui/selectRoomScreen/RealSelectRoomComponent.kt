package tablet.ui.selectRoomScreen

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tablet.domain.ISelectRoomInteractor
import tablet.domain.model.Booking

class RealSelectRoomComponent(componentContext: ComponentContext,
                              booking: Booking)
    : ComponentContext by componentContext, SelectRoomComponent, KoinComponent{
    private val interactor: ISelectRoomInteractor by inject()
    val booking = booking

    private var _state = MutableStateFlow(SelectRoomScreenState.defaultState)
    override val state = _state.asStateFlow()


    override fun bookRoom() {
        TODO("Not yet implemented")
    }

    override fun close() {
        TODO("Not yet implemented")
    }

}