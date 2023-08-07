package band.effective.office.tablet.ui.mainScreen.bookingRoomComponents

import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.store.BookingStore
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.store.BookingStoreFactory
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.uiComponents.pickerDateTime.DateTimePickerComponent
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.uiComponents.pickerDateTime.DateTimePickerStore
import band.effective.office.tablet.ui.mainScreen.mainScreen.store.MainStore
import band.effective.office.tablet.utils.componentCoroutineScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
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
    private val onChangeDate: (Calendar) -> Unit,
    private val onOpenDateTimePickerModal: () -> Unit,
    private val onCloseRequest: () -> Unit
) :
    ComponentContext by componentContext {

    private val bookingStore = instanceKeeper.getStore {
        BookingStoreFactory(storeFactory).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = bookingStore.stateFlow
    val labels = bookingStore.labels

    private fun collectLabels() = componentContext.componentCoroutineScope().launch {
        labels.collect{
            when(it){
                is BookingStore.Label.BookingCurrentRoom -> onCurrentBookingRoom()
                is BookingStore.Label.BookingOtherRoom -> onBookingOtherRoom()
                is BookingStore.Label.ChangeDate -> onChangeDate(state.value.selectDate.clone() as Calendar)
            }
        }
    }

    init {
        collectLabels()
    }

    val dateTimePickerComponent: DateTimePickerComponent =
        DateTimePickerComponent(
            componentContext = componentContext,
            storeFactory = storeFactory,
            onOpenDateTimePickerModal = { DateTimePickerStore.Intent.OnDateTimePickerModal() },
            onCloseRequest = { DateTimePickerStore.Intent.CloseModal() },
            changeDate = { day: Int, month: Int -> bookingStore.accept(BookingStore.Intent.OnSetDate(day, month)) }
        )

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
        bookingStore.accept(intent)
    }
}