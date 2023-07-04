package band.effective.office.tablet.ui.mainScreen.components.bookingRoomComponents

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
import java.util.Calendar
import java.util.GregorianCalendar

class RealBookingRoomComponent(
    private val componentContext: ComponentContext,
    private val onSelectOtherRoom: () -> Unit
) :
    ComponentContext by componentContext, BookingRoomComponent {
    private var mutableState = MutableStateFlow(BookingRoomState.default)
    override val state = mutableState.asStateFlow()

    override val dateTimeComponent: RealDateTimeComponent =
        RealDateTimeComponent(childContext("dateTime"), changeDay = { changeDate(it) })
    override val eventLengthComponent: RealEventLengthComponent =
        RealEventLengthComponent(childContext("length"),
            changeLength = { changeLength(it) })
    override val eventOrganizerComponent: RealEventOrganizerComponent =
        RealEventOrganizerComponent(childContext("organizer"))

    init {
        updateSelectTime()
    }

    override fun sendEvent(event: BookingRoomViewEvent) {
        when (event) {
            is BookingRoomViewEvent.OnBookingCurrentRoom -> {}
            is BookingRoomViewEvent.OnBookingOtherOtherRoom -> {
                onSelectOtherRoom()
            }
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

    //TODO
    private fun updateSelectTime() {
        componentCoroutineScope().launch {
            while (true) {
                val now = GregorianCalendar()
                mutableState.update { it.copy(selectDate = now) }
                delay((60 - now.get(Calendar.SECOND)) * 1000L)
            }
        }
    }
}

//https://gist.github.com/aartikov/a56cc94bb306e05b7b7927353910da08
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