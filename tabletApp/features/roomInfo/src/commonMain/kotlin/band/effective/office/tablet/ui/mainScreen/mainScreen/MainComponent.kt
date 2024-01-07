package band.effective.office.tablet.ui.mainScreen.mainScreen

import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.ui.bookingComponents.pickerDateTime.DateTimePickerComponent
import band.effective.office.tablet.ui.freeSelectRoom.FreeSelectRoomComponent
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.BookingRoomComponent
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.store.BookingStore
import band.effective.office.tablet.ui.mainScreen.mainScreen.store.MainFactory
import band.effective.office.tablet.ui.mainScreen.mainScreen.store.MainStore
import band.effective.office.tablet.ui.mainScreen.roomInfoComponents.RoomInfoComponent
import band.effective.office.tablet.ui.mainScreen.roomInfoComponents.store.RoomInfoStore
import band.effective.office.tablet.ui.mainScreen.slotComponent.SlotComponent
import band.effective.office.tablet.ui.selectRoomScreen.SelectRoomComponentImpl
import band.effective.office.tablet.ui.updateEvent.UpdateEventComponent
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
    private val OnSelectOtherRoomRequest: (() -> Booking) -> Unit,
    val onSettings: () -> Unit
) : ComponentContext by componentContext {

    val roomInfoComponent: RoomInfoComponent = RoomInfoComponent(
        componentContext = childContext(key = "roomInfoComponent"),
        storeFactory = storeFactory,
        onFreeRoomIntent = { mainStore.accept(MainStore.Intent.OnOpenFreeRoomModal) }

    )
    val bookingRoomComponent: BookingRoomComponent = BookingRoomComponent(
        componentContext = childContext(key = "bookingRoom"),
        onCurrentBookingRoom = { mainStore.accept(MainStore.Intent.OnBookingCurrentRoomRequest) },
        storeFactory = storeFactory,
        onBookingOtherRoom = { OnSelectOtherRoomRequest { onSelectOtherRoomRequest() } },
        onChangeDate = { roomInfoComponent.sendIntent(RoomInfoStore.Intent.OnChangeSelectDate(it)) },
    )

    val selectRoomComponent: SelectRoomComponentImpl =
        SelectRoomComponentImpl(
            componentContext = childContext(key = "bookingCurrentRoom"),
            storeFactory = storeFactory,
            onBookingRoom = { bookingRoomComponent.getBooking() },
            onBookingOtherRoom = { OnSelectOtherRoomRequest { onSelectOtherRoomRequest() } },
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

    val dateTimePickerComponent: DateTimePickerComponent =
        DateTimePickerComponent(
            componentContext = componentContext,
            storeFactory = storeFactory,
            onOpenDateTimePickerModal = { mainStore.accept(MainStore.Intent.OnOpenDateTimePickerModal) },
            onCloseRequest = { mainStore.accept(MainStore.Intent.CloseModal) },
            setNewDate = { day: Int, month: Int, year: Int, hour: Int, minute: Int ->
                bookingRoomComponent.sendIntent(
                    BookingStore.Intent.OnSetDate(
                        changedDay = day,
                        changedMonth = month,
                        changedYear = year,
                        changedHour = hour,
                        changedMinute = minute
                    )
                )
            },
        )

    val updateEventComponent = UpdateEventComponent(
        componentContext = componentContext,
        storeFactory = storeFactory
    )

    val slotComponent = SlotComponent(
        componentContext = componentContext,
        storeFactory = storeFactory,
        roomName = "Cadia"
    )

    private val mainStore = instanceKeeper.getStore {
        MainFactory(
            storeFactory = storeFactory
        ).create()
    }

    private fun onSelectOtherRoomRequest(): Booking {
        return bookingRoomComponent.getBooking()
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
