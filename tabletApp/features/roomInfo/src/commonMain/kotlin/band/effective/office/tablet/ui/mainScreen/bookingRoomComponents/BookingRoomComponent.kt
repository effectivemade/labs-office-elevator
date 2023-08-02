package band.effective.office.tablet.ui.mainScreen.bookingRoomComponents

import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.store.BookingStore
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.store.BookingStoreFactory
import band.effective.office.tablet.utils.componentCoroutineScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.GregorianCalendar

class BookingRoomComponent(
    private val componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val onCurrentBookingRoom: () -> Unit,
    private val onBookingOtherRoom: () -> Unit,
    private val onChangeDate: (Calendar) -> Unit
) :
    ComponentContext by componentContext {

    private val bookingStore = instanceKeeper.getStore {
        BookingStoreFactory(storeFactory).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = bookingStore.stateFlow

    init {
        componentContext.componentCoroutineScope().launch {
            state.collect { onChangeDate(state.value.selectDate.clone() as Calendar) }
        }
    }

    fun getBooking(): Booking {
        val startDate =
            if (state.value.isSelectCurrentTime) GregorianCalendar() else state.value.selectDate.clone() as Calendar
        val finishDate = startDate.clone() as Calendar
        finishDate.add(Calendar.MINUTE, state.value.length)

        return Booking(
            nameRoom = state.value.roomName,
            eventInfo = EventInfo(
                startTime = startDate,
                finishTime = finishDate,
                organizer = state.value.organizer
            )
        )
    }

    fun sendIntent(intent: BookingStore.Intent) {
        when (intent) {
            is BookingStore.Intent.OnBookingCurrentRoom -> {
                bookingStore.accept(intent.copy(onCurrentBookingRoom))
            }

            is BookingStore.Intent.OnBookingOtherRoom -> {
                bookingStore.accept(intent.copy(onBookingOtherRoom))
            }

            is BookingStore.Intent.OnChangeIsActive -> {
                bookingStore.accept(intent)
            }

            else -> bookingStore.accept(intent)
        }
    }
}