package band.effective.office.elevator.ui.authorization.profile_authorization.store

import band.effective.office.elevator.ui.models.User
import com.arkivanov.mvikotlin.core.store.Store

interface AuthProfileStore :
    Store<AuthProfileStore.Intent, AuthProfileStore.State, AuthProfileStore.Label> {

    sealed interface Intent {
        object ContinueButtonClicked : Intent
    }

    class State(val user: User, val currentTabIndex: Int)

    sealed interface Label {
        object AuthProfileSuccess : Label

        data class AuthProfileFailure(val message: String) : Label
    }
}