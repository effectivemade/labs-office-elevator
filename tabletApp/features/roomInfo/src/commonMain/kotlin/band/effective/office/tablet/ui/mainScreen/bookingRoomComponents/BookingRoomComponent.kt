package band.effective.office.tablet.ui.mainScreen.bookingRoomComponents

import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.store.BookingStore
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.store.BookingStoreFactory
import band.effective.office.tablet.utils.componentCoroutineScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.GregorianCalendar

class BookingRoomComponent(
    private val componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val onCurrentBookingRoom: () -> Unit,
    private val onBookingOtherRoom: () -> Unit
) :
    ComponentContext by componentContext {

    private val bookingStore = instanceKeeper.getStore {
        BookingStoreFactory(storeFactory).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = bookingStore.stateFlow

    val dateTimeComponent: RealDateTimeComponent =
        RealDateTimeComponent(
            childContext("dateTime"),
            changeDay = { bookingStore.accept(BookingStore.Intent.OnChangeDate(it)) }
        )
    val eventLengthComponent: RealEventLengthComponent =
        RealEventLengthComponent(childContext("length"),
            changeLength = { bookingStore.accept(BookingStore.Intent.OnChangeLength(it)) })
    val eventOrganizerComponent: RealEventOrganizerComponent =
        RealEventOrganizerComponent(
            childContext("organizer"),
            onSelectOrganizer = { bookingStore.accept(BookingStore.Intent.OnChangeOrganizer(it)) })

    fun bookingCurrentRoom() {
        if (state.value.isCorrect()) {
            onCurrentBookingRoom()
        }
    }

    fun bookingOtherRoom() {
        onBookingOtherRoom()
    }

    fun getBooking(): Booking {
        val finishDate = state.value.selectDate.clone() as Calendar
        finishDate.add(Calendar.MINUTE, state.value.length)

        return Booking(
            state.value.roomName,
            EventInfo(
                state.value.selectDate,
                finishDate,
                state.value.organizer
            )
        )
    }


    //TODO(Maksim Mishenko): think about while(true)
    private fun updateSelectTime() {
        componentCoroutineScope().launch {
            while (true) {
                val now = GregorianCalendar()
                //mutableState.update { it.copy(selectDate = now) }
                delay((60 - now.get(Calendar.SECOND)) * 1000L)
            }
        }
    }
}