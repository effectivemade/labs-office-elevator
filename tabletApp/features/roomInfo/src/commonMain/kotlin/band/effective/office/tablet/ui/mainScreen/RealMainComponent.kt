package band.effective.office.tablet.ui.mainScreen

import band.effective.office.tablet.domain.MockBooking
import band.effective.office.tablet.domain.RoomInteractor
import band.effective.office.tablet.ui.mainScreen.components.bookingRoomComponents.BookingRoomComponent
import band.effective.office.tablet.ui.mainScreen.components.bookingRoomComponents.RealBookingRoomComponent
import band.effective.office.tablet.ui.mainScreen.components.mockComponets.MockSettingsComponent
import band.effective.office.tablet.ui.mainScreen.components.mockComponets.RealMockSettingsComponent
import band.effective.office.tablet.ui.selectRoomScreen.RealSelectRoomComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RealMainComponent(
    componentContext: ComponentContext,
    private val OnSelectOtherRoomRequest: () -> Unit
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
        onBookingRoom = { sendEvent(it) },
        roomName = "Sirius"
    )
    override val selectRoomComponent: RealSelectRoomComponent =
        RealSelectRoomComponent(
            componentContext = childContext(key = "bookingCurrentRoom"),
            booking = MockBooking.bookingCheckTime15min,
            onCloseRequest = { mutableState.update { it.copy(showBookingModal = false) } }
        )

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
        bookingRoomComponent.update()
    }

    override fun sendEvent(event: MainScreenEvent) =
        when (event) {
            is MainScreenEvent.OnBookingCurentRoomRequest -> {
                mutableState.update { it.copy(showBookingModal = true) }
            }

            is MainScreenEvent.OnBookingOtherRoomRequest -> {
                OnSelectOtherRoomRequest()
            }
        }
}