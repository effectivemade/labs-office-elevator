package band.effective.office.elevator.ui.profile.store

import com.arkivanov.mvikotlin.core.store.Store

interface ProfileStore : Store<ProfileStore.Intent, ProfileStore.User, ProfileStore.Label> {

    sealed interface Intent {
        object SignOutClicked : Intent
    }

    data class User(
        val imageUrl: String?,
        val username: String?,
        val post:String?,
        val email: String?,
        val phone_number:String?
    )

    sealed interface Label {
        object OnSignedOut : Label
    }
}