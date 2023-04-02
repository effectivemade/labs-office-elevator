package band.effective.office.elevator.common.compose.screens.home

sealed class ElevatorMessageState(val message: String) {
    object Empty: ElevatorMessageState("")
    object Start: ElevatorMessageState("Press button to call elevator")
    object Loading: ElevatorMessageState("Attempt to call elevator")
    object IncorrectToken: ElevatorMessageState("Could not verify you Google account. Please try again or re-authenticate in app")
    object AuthorizationError: ElevatorMessageState("Authorization error. Please try again")
    object UndefinedError: ElevatorMessageState("Oops, something went wrong. Please try again")
    object Success: ElevatorMessageState("Success")
}