package band.effective.office.tablet.ui.mainScreen.mainScreen

import band.effective.office.tablet.ui.freeSelectRoom.FreeSelectRoomComponent
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.BookingRoomComponent
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.store.BookingStore
import band.effective.office.tablet.ui.mainScreen.mainScreen.store.MainFactory
import band.effective.office.tablet.ui.mainScreen.mainScreen.store.MainStore
import band.effective.office.tablet.ui.mainScreen.mockComponets.MockSettingsComponent
import band.effective.office.tablet.ui.mainScreen.mockComponets.RealMockSettingsComponent
import band.effective.office.tablet.ui.mainScreen.roomInfoComponents.RoomInfoComponent
import band.effective.office.tablet.ui.mainScreen.roomInfoComponents.store.RoomInfoStore
import band.effective.office.tablet.ui.selectRoomScreen.SelectRoomComponentImpl
import band.effective.office.tablet.utils.componentCoroutineScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class MainComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val OnSelectOtherRoomRequest: () -> Unit
) : ComponentContext by componentContext {

    val mockSettingsComponent: MockSettingsComponent =
        RealMockSettingsComponent(
            componentContext = childContext(key = "mock")
        )
    val roomInfoComponent: RoomInfoComponent = RoomInfoComponent(
        componentContext = childContext(key = "roomInfoComponent"),
        storeFactory = storeFactory,
        onFreeRoomIntent = { mainStore.accept(MainStore.Intent.OnOpenFreeRoomModal) }

    )
    val bookingRoomComponent: BookingRoomComponent = BookingRoomComponent(
        componentContext = childContext(key = "bookingRoom"),
        onCurrentBookingRoom = { mainStore.accept(MainStore.Intent.OnBookingCurrentRoomRequest) },
        storeFactory = storeFactory,
        onBookingOtherRoom = { OnSelectOtherRoomRequest() },
        onChangeDate = { roomInfoComponent.sendIntent(RoomInfoStore.Intent.OnChangeSelectDate(it)) }
    )

    val selectRoomComponent: SelectRoomComponentImpl =
        SelectRoomComponentImpl(
            componentContext = childContext(key = "bookingCurrentRoom"),
            storeFactory = storeFactory,
            onBookingRoom = { bookingRoomComponent.getBooking() },
            onBookingOtherRoom = { OnSelectOtherRoomRequest() },
            onMainScreen = {
                mainStore.accept(MainStore.Intent.CloseModal)
                bookingRoomComponent.sendIntent(BookingStore.Intent.OnChangeIsActive(true))
                           },
            onCloseRequest = {
                mainStore.accept(MainStore.Intent.CloseModal)
                bookingRoomComponent.sendIntent(BookingStore.Intent.OnChangeIsActive(false))
            }
        )

    val freeSelectRoomComponent: FreeSelectRoomComponent =
        FreeSelectRoomComponent(
            componentContext = componentContext,
            storeFactory = storeFactory,
            onCloseRequest = { mainStore.accept(MainStore.Intent.CloseModal) })

    private val mainStore = instanceKeeper.getStore {
        MainFactory(
            storeFactory = storeFactory
        ).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = mainStore.stateFlow

    fun sendIntent(intent: MainStore.Intent) {
        mainStore.accept(intent)
    }

    init {
        componentContext.componentCoroutineScope().launch {
            roomInfoComponent.state.collect {
                mainStore.accept(
                    MainStore.Intent.OnDisconnectChange(
                        it.isError
                    )
                )
            }
        }
    }
}
