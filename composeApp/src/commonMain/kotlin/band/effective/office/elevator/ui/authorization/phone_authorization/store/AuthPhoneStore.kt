package band.effective.office.elevator.ui.authorization.phone_authorization.store

import com.arkivanov.mvikotlin.core.store.Store

interface AuthPhoneStore :
    Store<AuthPhoneStore.Intent, AuthPhoneStore.State, AuthPhoneStore.Label> {

    sealed interface Intent {
        object ContinueButtonClicked : Intent
    }

    class State

    sealed interface Label {
        data class AuthPhoneSuccess(val phone: String) : Label

        data class AuthPhoneFailure(val message: String) : Label
    }
}