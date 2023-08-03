package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen

import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.store.FreeNegotiationsStore
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.store.FreeNegotiationsStoreFactory
import band.effective.office.tablet.ui.selectRoomScreen.SelectRoomComponentImpl
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.component.KoinComponent
import java.util.Calendar


class FreeNegotiationsComponentImpl(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    val onBookingInfo: () -> Booking,
    val onMainScreen: (reset: Boolean) -> Unit
) : ComponentContext by componentContext, FreeNegotiationsComponent, KoinComponent {
    override fun onIntent(intent: FreeNegotiationsStore.Intent) {
        when (intent) {
            is FreeNegotiationsStore.Intent.SetBooking ->
                freeNegotiationsStore.accept(intent.copy(bookingInfo = onBookingInfo()))

            is FreeNegotiationsStore.Intent.OnMainScreen -> {
                onMainScreen(intent.reset)
                freeNegotiationsStore.accept(intent)
            }

            is FreeNegotiationsStore.Intent.OnBookingRoom -> {
                freeNegotiationsStore.accept(intent)
            }

            is FreeNegotiationsStore.Intent.CloseModal ->
                freeNegotiationsStore.accept(intent)
        }
    }

    private val freeNegotiationsStore = instanceKeeper.getStore {
        FreeNegotiationsStoreFactory(
            storeFactory = storeFactory
        ).create()
    }

    override val selectRoomComponent: SelectRoomComponentImpl =
        SelectRoomComponentImpl(
            componentContext = childContext(key = "bookingCurrentRoom"),
            storeFactory = storeFactory,
            onBookingRoom = {
                Booking(
                    nameRoom = state.value.nameBookingRoom,
                    eventInfo = EventInfo(
                        startTime = state.value.currentTime,
                        finishTime = getFinishTime(state.value.currentTime, state.value.realDurationBooking),
                        organizer = state.value.organizer
                    )
                )
            },
            onBookingOtherRoom = {},
            onMainScreen = { onIntent(FreeNegotiationsStore.Intent.OnMainScreen(reset = true)) },
            onCloseRequest = { onIntent(FreeNegotiationsStore.Intent.CloseModal) }
        )

    private fun getFinishTime(startTime: Calendar, duration: Int): Calendar {
        val finishDate = startTime.clone() as Calendar
        finishDate.add(Calendar.MINUTE, duration)
        return finishDate
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state = freeNegotiationsStore.stateFlow
}