package band.effective.office.elevator.ui.authorization.authorizationphone_page

import com.arkivanov.mvikotlin.core.store.Store

interface AuthorizationPhoneStore :
    Store<AuthorizationPhoneStore.Intent, AuthorizationPhoneStore.State, AuthorizationPhoneStore.Label> {

    sealed interface Intent {
        data class PhoneNumberChanged(val phoneNumber: String) : Intent
        object ContinueButtonClicked : Intent
        object BackButtonClicked : Intent
    }

    data class State(
        val phoneNumber: String = "+7",
        val isValidPhoneNumber: Boolean = false,
        val isLoading: Boolean = false,
        val isError: Boolean = false
    )

    sealed interface Label {
        object AuthorizationPhoneSuccess : Label

        object AuthorizationPhoneFailure : Label
    }
}
