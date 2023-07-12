package band.effective.office.elevator.ui.profile.mainProfile.store

import com.arkivanov.mvikotlin.core.store.Store

interface ProfileStore : Store<ProfileStore.Intent, ProfileStore.User, ProfileStore.Label> {

    sealed interface Intent {
        object SignOutClicked : Intent
        object EditProfileClicked : Intent
    }

    data class User(
        val imageUrl: String?,
        val username: String?,
        val post:String?,
        val phoneNumber:String?,
        val telegram: String?
    )

    sealed interface Label {
        object OnSignedOut : Label
        object OnClickedEdit: Label
    }
}