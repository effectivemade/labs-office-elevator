package band.effective.office.elevator.ui.authorization.authorization_phone.store

import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.ui.authorization.authorization_google.store.AuthorizationGoogleStore
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
        var idToken: String? = null,
        var isErrorPhoneNumber: Boolean = false,
        var isLoading: Boolean = false,
        var isError: Boolean = false
    )

    sealed interface Label {
        data class AuthorizationPhoneSuccess(val userData: User.UserData) : Label

        object AuthorizationPhoneFailure : Label

        object ReturnInGoogleAuthorization : Label
    }
}
