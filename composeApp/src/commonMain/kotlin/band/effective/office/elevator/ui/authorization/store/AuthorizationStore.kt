package band.effective.office.elevator.ui.authorization.store

import com.arkivanov.mvikotlin.core.store.Store

interface AuthorizationStore :
    Store<AuthorizationStore.Intent, AuthorizationStore.State, AuthorizationStore.Label> {

    sealed interface Intent {
        object SignInButtonClicked : Intent
    }

    class State

    sealed interface Label {
        object AuthorizationSuccess : Label
        data class AuthorizationFailure(val message: String) : Label
    }
}