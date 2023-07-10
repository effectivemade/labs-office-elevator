package band.effective.office.tablet.ui.mainScreen.components.mockComponets

sealed interface MockSettingsEvent{
    data class OnSwitchEventCount(val newState: Boolean): MockSettingsEvent
    data class OnSwitchBusy(val newState: Boolean): MockSettingsEvent
    data class OnSwitchTv(val newState: Boolean): MockSettingsEvent
    data class OnSwitchBusyTime(val newState: Boolean): MockSettingsEvent
    object OnSwitchVisible: MockSettingsEvent

}