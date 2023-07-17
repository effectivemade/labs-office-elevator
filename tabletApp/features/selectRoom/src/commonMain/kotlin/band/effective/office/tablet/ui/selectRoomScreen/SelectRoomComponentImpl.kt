package band.effective.office.tablet.ui.selectRoomScreen

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import band.effective.office.tablet.domain.SelectRoomInteractor
import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.ui.selectRoomScreen.store.SelectRoomStore
import band.effective.office.tablet.ui.selectRoomScreen.store.SelectRoomStoreFactory
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi

class SelectRoomComponentImpl(
    componentContext: ComponentContext,
    val booking: Booking,
    storeFactory: StoreFactory,
    private val onCloseRequest: () -> Unit
) : ComponentContext by componentContext, SelectRoomComponent, KoinComponent {
   // private val interactor: SelectRoomInteractor by inject()

    private val bookingStore = instanceKeeper.getStore {
        SelectRoomStoreFactory(storeFactory).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = bookingStore.stateFlow

    override fun bookRoom() {
        bookingStore.accept(SelectRoomStore.Intent.BookingRoom)
    }

    fun close() {
        onCloseRequest()
        bookingStore.accept(SelectRoomStore.Intent.CloseModal)
    }

}