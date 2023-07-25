package band.effective.office.tablet.ui.selectRoomScreen

import com.arkivanov.decompose.ComponentContext
import org.koin.core.component.KoinComponent
import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.ui.selectRoomScreen.store.SelectRoomStore
import band.effective.office.tablet.ui.selectRoomScreen.store.SelectRoomStoreFactory
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi

class SelectRoomComponentImpl(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    val onBookingRoom: () -> Booking,
    private val onCloseRequest: () -> Unit
) : ComponentContext by componentContext, SelectRoomComponent, KoinComponent {

    private val bookingStore = instanceKeeper.getStore {
        SelectRoomStoreFactory(storeFactory).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state = bookingStore.stateFlow

    override fun onIntent(intent: SelectRoomStore.Intent){
        when (intent) {
            is SelectRoomStore.Intent.BookingRoom -> {
                bookingStore.accept(intent)
            }
            is SelectRoomStore.Intent.CloseModal -> {
                onCloseRequest()
                bookingStore.accept(intent)
            }
            is SelectRoomStore.Intent.SetBooking -> {
                bookingStore.accept(intent.copy(booking = onBookingRoom()))
            }
        }
    }

}