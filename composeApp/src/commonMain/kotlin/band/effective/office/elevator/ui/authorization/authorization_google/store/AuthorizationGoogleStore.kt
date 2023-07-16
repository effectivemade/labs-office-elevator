package band.effective.office.elevator.ui.authorization.authorization_google.store

import com.arkivanov.mvikotlin.core.store.Store

interface AuthorizationGoogleStore :
    Store<AuthorizationGoogleStore.Intent, AuthorizationGoogleStore.State, AuthorizationGoogleStore.Label> {

    sealed interface Intent {
        object SignInButtonClicked : Intent
    }

    class State

    sealed interface Label {
        object AuthorizationSuccess : Label

        data class AuthorizationFailure(val message: String) : Label
    }
}