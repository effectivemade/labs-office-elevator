package band.effective.office.tablet.ui.mainScreen

import band.effective.office.tablet.domain.useCase.UpdateUseCase
import band.effective.office.tablet.ui.mainScreen.components.bookingRoomComponents.BookingRoomComponent
import band.effective.office.tablet.ui.mainScreen.components.bookingRoomComponents.RealBookingRoomComponent
import band.effective.office.tablet.ui.mainScreen.components.mockComponets.MockSettingsComponent
import band.effective.office.tablet.ui.mainScreen.components.mockComponets.RealMockSettingsComponent
import band.effective.office.tablet.utils.componentCoroutineScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RealMainComponent(
    componentContext: ComponentContext,
    private val OnSelectOtherRoomRequest: () -> Unit
) : ComponentContext by componentContext, KoinComponent, MainComponent {

    private val updateUseCase: UpdateUseCase by inject()

    private val scope = componentContext.componentCoroutineScope()

    private var mutableState = MutableStateFlow(MainScreenState.defaultState)
    override val state = mutableState.asStateFlow()

    override val mockSettingsComponent: MockSettingsComponent =
        RealMockSettingsComponent(
            componentContext = childContext(key = "mock"),
            updateData = { /*updateData()*/ }
        )
    override val bookingRoomComponent: BookingRoomComponent = RealBookingRoomComponent(
        componentContext = childContext(key = "bookingRoom"),
        onSelectOtherRoom = { OnSelectOtherRoomRequest() },
        roomName = "Sirius"
    )

    init {
        updateData()
    }

    private fun updateData() = scope.launch {
        updateUseCase(
            scope = scope,
            roomUpdateHandler = { roomInfo -> mutableState.update { it.copy(roomInfo = roomInfo) } },
            organizerUpdateHandler = {})
        mutableState.update {
            it.copy(
                isLoad = false,
                isData = true,
                roomInfo = updateUseCase.getRoomInfo()
            )
        }
    }

    override fun sendEvent(event: MainScreenEvent) =
        when (event) {
            is MainScreenEvent.OnCLick -> {
                OnSelectOtherRoomRequest()
            }
        }
}