package band.effective.office.elevator.ui.profile.store

import com.arkivanov.mvikotlin.core.store.Store

interface ProfileStore : Store<ProfileStore.Intent, ProfileStore.State, ProfileStore.Label> {

    sealed interface Intent {
        object SignOutClicked : Intent
    }

    data class State(
        val imageUrl: String?,
        val username: String?,
        val email: String?,
    )

    sealed interface Label {
        object OnSignedOut : Label
    }
}