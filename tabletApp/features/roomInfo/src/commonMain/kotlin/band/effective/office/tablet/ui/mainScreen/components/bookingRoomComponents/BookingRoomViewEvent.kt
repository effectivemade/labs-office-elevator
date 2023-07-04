package band.effective.office.tablet.ui.mainScreen.components.bookingRoomComponents

sealed interface BookingRoomViewEvent{
    object OnBookingCurrentRoom: BookingRoomViewEvent
    object OnBookingOtherOtherRoom: BookingRoomViewEvent
}