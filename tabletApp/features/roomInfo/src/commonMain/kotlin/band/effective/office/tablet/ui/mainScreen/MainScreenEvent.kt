package band.effective.office.tablet.ui.mainScreen

sealed class MainScreenEvent {
    object OnBookingCurentRoomRequest: MainScreenEvent()
    object OnBookingOtherRoomRequest: MainScreenEvent()
}