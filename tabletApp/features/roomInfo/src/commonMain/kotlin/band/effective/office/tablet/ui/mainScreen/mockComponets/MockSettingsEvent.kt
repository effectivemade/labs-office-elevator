package band.effective.office.tablet.ui.mainScreen.mockComponets

sealed interface MockSettingsEvent{
    data class OnSwitchEventCount(val newState: Boolean): MockSettingsEvent
    data class OnSwitchBusy(val newState: Boolean): MockSettingsEvent
    data class OnSwitchTv(val newState: Boolean): MockSettingsEvent
    object OnSwitchVisible: MockSettingsEvent

}