package band.effective.office.elevator.ui.profile.store

import com.arkivanov.mvikotlin.core.store.Store

interface ProfileEditStore : Store<ProfileEditStore.Intent,ProfileEditStore.User,ProfileEditStore.Label>{
    sealed interface Intent {
        object BackInProfileClicked : Intent
        object SaveChangeClicked : Intent
    }

    data class User(
        val username: String?,
        val post:String?,
        val phoneNumber:String?,
        val telegram: String?
    )

    sealed interface Label {
        object ReturnedInProfile : Label
        object SavedChange : Intent
    }
}