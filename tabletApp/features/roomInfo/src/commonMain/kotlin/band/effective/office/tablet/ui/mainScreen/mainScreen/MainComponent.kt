package band.effective.office.tablet.ui.mainScreen.mainScreen

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import band.effective.office.tablet.domain.MockBooking
import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.BookingRoomComponent
import band.effective.office.tablet.ui.mainScreen.mainScreen.store.MainStore
import band.effective.office.tablet.ui.mainScreen.mainScreen.store.MainStoreFactory
import band.effective.office.tablet.ui.mainScreen.mockComponets.MockSettingsComponent
import band.effective.office.tablet.ui.mainScreen.mockComponets.RealMockSettingsComponent
import band.effective.office.tablet.ui.selectRoomScreen.RealFreeSelectRoomComponent
import band.effective.office.tablet.ui.selectRoomScreen.SelectRoomComponentImpl
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MainComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val OnSelectOtherRoomRequest: () -> Unit
) : ComponentContext by componentContext {

    val mockSettingsComponent: MockSettingsComponent =
        RealMockSettingsComponent(
            componentContext = childContext(key = "mock")
        )
    val bookingRoomComponent: BookingRoomComponent = BookingRoomComponent(
        componentContext = childContext(key = "bookingRoom"),
        onCurrentBookingRoom = { mainStore.accept(MainStore.Intent.OnBookingCurrentRoomRequest) },
        storeFactory = storeFactory,
        onBookingOtherRoom = { OnSelectOtherRoomRequest() }
    )

    val selectRoomComponent: SelectRoomComponentImpl =
        SelectRoomComponentImpl(
            componentContext = childContext(key = "bookingCurrentRoom"),
            storeFactory = storeFactory,
            onBookingRoom = { bookingRoomComponent.getBooking() },
            onCloseRequest = { mainStore.accept(MainStore.Intent.CloseModal) }
        )

    val freeSelectRoomComponent: RealFreeSelectRoomComponent =
        RealFreeSelectRoomComponent(
            componentContext = childContext(key = "freeSelectRoom"),
            onCloseRequest = { closeAllModal() }
        )

    private val mainStore = instanceKeeper.getStore {
        MainStoreFactory(
            storeFactory = storeFactory
        ).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = mainStore.stateFlow

    fun closeAllModal(){
        mainStore.accept(MainStore.Intent.CloseModal)
    }

    fun openFreeRoomModal(){
        mainStore.accept(MainStore.Intent.OnOpenFreeRoomModal)
    }

    fun onFreeRoom(){
        mainStore.accept(MainStore.Intent.OnFreeRoomIntent)
    }
}
