package band.effective.office.elevator.ui.elevator.store

import com.arkivanov.mvikotlin.core.store.Store
import dev.icerock.moko.resources.StringResource

interface ElevatorStore : Store<ElevatorStore.Intent, ElevatorStore.State, ElevatorStore.Label> {

    sealed interface Intent {
        object OnButtonClicked : Intent
    }

    sealed interface Label {
        data class ShowError(val errorState: ErrorState) : Label
        object ShowSuccess : Label
    }

    data class State(val buttonActive: Boolean)

    data class ErrorState(val message: StringResource)
}