package band.effective.office.elevator.ui.authorization.tg_authorization.store

import band.effective.office.elevator.ui.models.User
import com.arkivanov.mvikotlin.core.store.Store

interface AuthTGStore :
    Store<AuthTGStore.Intent, AuthTGStore.State, AuthTGStore.Label> {

    sealed interface Intent {
        object ContinueButtonClicked : Intent
    }

    class State(val user: User, val currentTabIndex: Int)

    sealed interface Label {
        object AuthTGSuccess : Label

        data class AuthTGFailure(val message: String) : Label
    }
}