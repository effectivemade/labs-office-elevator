package band.effective.office.elevator.ui.root.store

import com.arkivanov.mvikotlin.core.store.Store

interface RootStore :
    Store<Nothing, RootStore.State, RootStore.Label> {

    sealed interface Label {
        object UserAlreadySigned : Label
        object UserNotSigned : Label
    }

    class State
}