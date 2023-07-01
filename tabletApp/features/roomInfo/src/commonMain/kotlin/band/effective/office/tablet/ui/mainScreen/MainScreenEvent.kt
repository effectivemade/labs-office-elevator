package band.effective.office.tablet.ui.mainScreen

sealed class MainScreenEvent {
    object OnCLick: MainScreenEvent()
    object OnDoubleTub: MainScreenEvent()
}