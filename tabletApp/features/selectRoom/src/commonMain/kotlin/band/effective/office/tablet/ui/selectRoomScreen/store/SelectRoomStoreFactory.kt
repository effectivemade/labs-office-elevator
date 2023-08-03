package band.effective.office.tablet.ui.selectRoomScreen.store

import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.domain.model.Either
import band.effective.office.tablet.domain.useCase.BookingUseCase
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SelectRoomStoreFactory(private val storeFactory: StoreFactory) : KoinComponent {
    val bookingUseCase: BookingUseCase by inject()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): SelectRoomStore =
        object : SelectRoomStore, Store<SelectRoomStore.Intent, SelectRoomStore.State, Nothing>
        by storeFactory.create(
            name = "SelectStore",
            initialState = SelectRoomStore.State.defaultState,
            bootstrapper = coroutineBootstrapper {},
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Message {
        data class BookingRoom(val isSuccess: Boolean) : Message
        object CloseModal : Message
        data class SetBooking(val booking: Booking) : Message
        object StartLoading: Message
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<SelectRoomStore.Intent, Nothing, SelectRoomStore.State, Message, Nothing>() {
        override fun executeIntent(
            intent: SelectRoomStore.Intent,
            getState: () -> SelectRoomStore.State
        ) {
            when (intent) {
                is SelectRoomStore.Intent.BookingRoom -> bookingRoom(getState())
                is SelectRoomStore.Intent.CloseModal -> dispatch(Message.CloseModal)
                is SelectRoomStore.Intent.SetBooking -> dispatch(Message.SetBooking(intent.booking))
                is SelectRoomStore.Intent.BookingOtherRoom -> dispatch(Message.CloseModal)
            }
        }

        private fun bookingRoom(state: SelectRoomStore.State) =
            scope.launch {
                dispatch(Message.StartLoading)
                dispatch(Message.BookingRoom(bookingUseCase(state.booking.eventInfo) is Either.Success))
            }
    }

    private object ReducerImpl : Reducer<SelectRoomStore.State, Message> {
        override fun SelectRoomStore.State.reduce(message: Message): SelectRoomStore.State =
            when (message) {
                is Message.BookingRoom ->
                    if (message.isSuccess) copy(
                        isData = false,
                        isSuccess = true,
                        error = null,
                        isLoading = false
                    ) else copy(error = "error", isLoading = false)

                is Message.CloseModal -> SelectRoomStore.State.defaultState
                is Message.SetBooking -> copy(booking = message.booking)
                is Message.StartLoading -> copy(isLoading = true)
            }
    }


}