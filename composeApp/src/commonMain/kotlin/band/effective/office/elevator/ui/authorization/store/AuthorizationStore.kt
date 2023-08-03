package band.effective.office.elevator.ui.authorization.store

import band.effective.office.elevator.domain.models.UserData
import com.arkivanov.mvikotlin.core.store.Store

interface AuthorizationStore :
    Store<AuthorizationStore.Intent, AuthorizationStore.State, AuthorizationStore.Label> {

    sealed interface Intent {
        object ContinueButtonClicked : Intent
    }

    data class State(
        val userData: UserData = UserData()
    )

    sealed interface Label {
        object AuthorizationSuccess : Label
    }
}