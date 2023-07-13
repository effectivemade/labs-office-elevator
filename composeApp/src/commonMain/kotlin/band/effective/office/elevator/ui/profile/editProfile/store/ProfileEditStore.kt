package band.effective.office.elevator.ui.profile.editProfile.store

import com.arkivanov.mvikotlin.core.store.Store

interface ProfileEditStore : Store<ProfileEditStore.Intent, ProfileEditStore.User, ProfileEditStore.Label>{
    sealed interface Intent {
        object BackInProfileClicked : Intent
        object SaveChangeClicked : Intent
    }

    data class User(
        val userName: String?,
        val post:String?,
        val phoneNumber:String?,
        val telegram: String?
    )

    sealed interface Label {
        object ReturnedInProfile : Label
        object SavedChange : Label
    }
}