package band.effective.office.tablet.ui.mainScreen

import band.effective.office.tablet.domain.RoomInteractor
import band.effective.office.tablet.ui.mainScreen.components.bookingRoomComponents.BookingRoomComponent
import band.effective.office.tablet.ui.mainScreen.components.bookingRoomComponents.RealBookingRoomComponent
import band.effective.office.tablet.ui.mainScreen.components.mockComponets.MockSettingsComponent
import band.effective.office.tablet.ui.mainScreen.components.mockComponets.RealMockSettingsComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RealMainComponent(
    componentContext: ComponentContext,
    private val onClick: () -> Unit
) : ComponentContext by componentContext, KoinComponent, MainComponent {

    private val interactor: RoomInteractor by inject()

    private var mutableState = MutableStateFlow(MainScreenState.defaultState)
    override val state = mutableState.asStateFlow()

    override val mockSettingsComponent: MockSettingsComponent =
        RealMockSettingsComponent(
            componentContext = childContext(key = "mock"),
            updateData = { updateData() }
        )
    override val bookingRoomComponent: BookingRoomComponent = RealBookingRoomComponent(
        componentContext = childContext(key = "bookingRoom"),
        onSelectOtherRoom = { onClick() })

    init {
        updateData()
    }

    private fun updateData() {
        mutableState.update {
            it.copy(
                isLoad = false,
                isData = true,
                roomInfo = interactor.getRoomInfo("sirius")
            )
        }
    }

    override fun sendEvent(event: MainScreenEvent) =
        when (event) {
            is MainScreenEvent.OnCLick -> {
                onClick()
            }

            is MainScreenEvent.OnDoubleTub -> {}
        }
}