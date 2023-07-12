package band.effective.office.elevator.ui.authorization.profile_authorization.store

import com.arkivanov.mvikotlin.core.store.Store

interface AuthProfileStore :
    Store<AuthProfileStore.Intent, AuthProfileStore.State, AuthProfileStore.Label> {

    sealed interface Intent {
        object ContinueButtonClicked : Intent
    }

    class State

    sealed interface Label {
        object AuthProfileSuccess : Label

        data class AuthProfileFailure(val message: String) : Label
    }
}