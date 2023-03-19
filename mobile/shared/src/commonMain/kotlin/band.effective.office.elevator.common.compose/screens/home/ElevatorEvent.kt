package band.effective.office.elevator.common.compose.screens.home

sealed class ElevatorEvent {
    object SignOut : ElevatorEvent()
    object CallElevator : ElevatorEvent()
}