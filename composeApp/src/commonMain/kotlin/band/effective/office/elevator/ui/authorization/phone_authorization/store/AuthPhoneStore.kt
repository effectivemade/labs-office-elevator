package band.effective.office.elevator.ui.authorization.phone_authorization.store

import band.effective.office.elevator.ui.models.User
import com.arkivanov.mvikotlin.core.store.Store

interface AuthPhoneStore :
    Store<AuthPhoneStore.Intent, AuthPhoneStore.State, AuthPhoneStore.Label> {

    sealed interface Intent {
        object ContinueButtonClicked : Intent
    }

    class State (val user: User, val currentTabIndex: Int)

    sealed interface Label {
        data class AuthPhoneSuccess(val phone: String) : Label

        data class AuthPhoneFailure(val message: String) : Label
    }
}