package band.effective.office.elevator.ui.main.store

import band.effective.office.elevator.ui.models.ElevatorState
import band.effective.office.elevator.ui.models.ReservedSeat
import com.arkivanov.mvikotlin.core.store.Store
import dev.icerock.moko.resources.StringResource

interface MainStore : Store<MainStore.Intent, MainStore.State, MainStore.Label> {

    sealed interface Intent {
        object OnClickCallElevator : Intent

        object  OnClickShowOption : Intent
    }

    sealed interface Label {
        data class ShowError(val errorState: ErrorState) : Label
        object ShowSuccess : Label

        object ShowOptions : Label

    }

    data class State(
        val reservedSeats: List<ReservedSeat>,
        val elevatorState: ElevatorState
    )

    data class ErrorState(val message: StringResource)
}