package band.effective.office.elevator.ui.authorization.screen_2.store

import com.arkivanov.mvikotlin.core.store.Store

interface AuthTab1Store :
    Store<AuthTab1Store.Intent, AuthTab1Store.State, AuthTab1Store.Label> {

    sealed interface Intent {
        object ContinueButtonClicked : Intent
    }

    class State

    sealed interface Label {

    }
}