package band.effective.office.elevator.ui.authorization.authorization_phone.store

import com.arkivanov.mvikotlin.core.store.Store

interface AuthorizationPhoneStore :
    Store<AuthorizationPhoneStore.Intent, AuthorizationPhoneStore.State, AuthorizationPhoneStore.Label> {

    sealed interface Intent {
        data class PhoneNumberChanged(val phoneNumber: String) : Intent
        object ContinueButtonClicked : Intent
        object BackButtonClicked : Intent
    }

    data class State(
        var phoneNumber: String = "",
        var isErrorPhoneNumber: Boolean = false,
        var isLoading: Boolean = false,
        var isError: Boolean = false
    )

    sealed interface Label {
        object AuthorizationPhoneSuccess : Label

        object AuthorizationPhoneFailure : Label

        object ReturnInGoogleAuthorization : Label

        object OpenProfileAuthorization : Label
    }
}
