package band.effective.office.tablet.ui.mainScreen.components.bookingRoomComponents

import band.effective.office.tablet.domain.RoomInteractor
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.ui.mainScreen.MainScreenEvent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Calendar
import java.util.GregorianCalendar

class RealBookingRoomComponent(
    private val componentContext: ComponentContext,
    private val onBookingRoom: (MainScreenEvent) -> Unit,
    roomName: String
) :
    ComponentContext by componentContext, BookingRoomComponent, KoinComponent {
    private var mutableState = MutableStateFlow(BookingRoomState.default)
    override val state = mutableState.asStateFlow()

    override val dateTimeComponent: RealDateTimeComponent =
        RealDateTimeComponent(
            childContext("dateTime"),
            changeDay = { changeDate(it) }
        )
    override val eventLengthComponent: RealEventLengthComponent =
        RealEventLengthComponent(childContext("length"),
            changeLength = { changeLength(it) })
    override val eventOrganizerComponent: RealEventOrganizerComponent =
        RealEventOrganizerComponent(
            childContext("organizer"),
            onSelectOrganizer = { selectOrganizer(it) })

    private val roomInfoInteractor: RoomInteractor by inject()

    init {
        mutableState.update { it.copy(roomName = roomName) }
        updateSelectTime()
        update()
    }

    override fun sendEvent(event: BookingRoomViewEvent) {
        when (event) {
            is BookingRoomViewEvent.OnBookingCurrentRoom -> {
                onBookingRoom(MainScreenEvent.OnBookingCurentRoomRequest)
            }

            is BookingRoomViewEvent.OnBookingOtherRoom -> {
                onBookingRoom(MainScreenEvent.OnBookingOtherRoomRequest)
            }
        }
    }

    override fun update() {
        mutableState.update {
            it.copy(
                organizers = roomInfoInteractor.getOrganizers(),
                isBusy = roomInfoInteractor.checkRoom("", GregorianCalendar()) != null,
                busyEvent = roomInfoInteractor.checkRoom("", GregorianCalendar())
                    ?: EventInfo.emptyEvent,
            )
        }
    }

    private fun changeLength(delta: Int) {
        if (state.value.length + delta >= 0) {
            mutableState.update { it.copy(length = it.length + delta) }
        }
    }

    private fun changeDate(day: Int) {
        val newValue = state.value.selectDate.clone() as Calendar
        newValue.add(Calendar.DAY_OF_MONTH, day)
        mutableState.update { it.copy(selectDate = newValue) }
    }

    //TODO(Maksim Mishenko): think about while(true)
    private fun updateSelectTime() {
        componentCoroutineScope().launch {
            while (true) {
                val now = GregorianCalendar()
                mutableState.update { it.copy(selectDate = now) }
                delay((60 - now.get(Calendar.SECOND)) * 1000L)
            }
        }
    }

    private fun selectOrganizer(organizer: String) {
        mutableState.update { it.copy(organizer = organizer) }
    }
}

//NOTE(Maksim Mishenko): https://gist.github.com/aartikov/a56cc94bb306e05b7b7927353910da08
fun ComponentContext.componentCoroutineScope(): CoroutineScope {
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    if (lifecycle.state != Lifecycle.State.DESTROYED) {
        lifecycle.doOnDestroy {
            scope.cancel()
        }
    } else {
        scope.cancel()
    }

    return scope
}