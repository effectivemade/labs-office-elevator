package band.effective.office.elevator.ui.booking

import band.effective.office.elevator.ui.booking.store.BookingStore
import band.effective.office.elevator.ui.booking.store.BookingStoreFactory
import band.effective.office.elevator.ui.main.MainComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class BookingComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (BookingComponent.Output) -> Unit
) :
    ComponentContext by componentContext {

    private val bookingStore = instanceKeeper.getStore {
        BookingStoreFactory(
            storeFactory = storeFactory
        ).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<BookingStore.State> = bookingStore.stateFlow

    val label: Flow<BookingStore.Label> = bookingStore.labels
    fun onEvent(event: BookingStore.Intent) {
        bookingStore.accept(event)
    }

    fun onOutput(output: BookingComponent.Output) {
        output(output)
    }

    sealed class Output {
        object OpenMainTab : Output()
    }
}