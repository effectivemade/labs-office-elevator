package band.effective.office.tablet.ui.mainScreen.components.bookingRoomComponents

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RealBookingRoomComponent(
    componentContext: ComponentContext,
    private val onSelectOtherRoom: () -> Unit
) :
    ComponentContext by componentContext, BookingRoomComponent {
    private var mutableState = MutableStateFlow(BookingRoomState.default)
    override val state = mutableState.asStateFlow()

    override val dateTimeComponent: RealDateTimeComponent =
        RealDateTimeComponent(childContext("dateTime"))
    override val eventLengthComponent: RealEventLengthComponent =
        RealEventLengthComponent(childContext("length"),
            changeLength = { changeLength(it) })
    override val eventOrganizerComponent: RealEventOrganizerComponent =
        RealEventOrganizerComponent(childContext("organizer"))

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
}