package band.effective.office.tablet.ui.mainScreen.components.bookingRoomComponents

import kotlinx.coroutines.flow.StateFlow

interface BookingRoomComponent {
    val state: StateFlow<BookingRoomState>

    val dateTimeComponent: RealDateTimeComponent
    val eventLengthComponent: RealEventLengthComponent
    val eventOrganizerComponent: RealEventOrganizerComponent

    fun sendEvent(event: BookingRoomViewEvent)
    fun update()
}