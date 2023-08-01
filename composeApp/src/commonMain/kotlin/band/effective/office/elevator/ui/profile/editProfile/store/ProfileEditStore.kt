package band.effective.office.elevator.ui.profile.editProfile.store

import band.effective.office.elevator.domain.models.User
import com.arkivanov.mvikotlin.core.store.Store

interface ProfileEditStore : Store<ProfileEditStore.Intent, ProfileEditStore.State, ProfileEditStore.Label>{
    sealed interface Intent {
        object BackInProfileClicked : Intent
        data class SaveChangeClicked (val userName:String, val telegram:String, val post:String, val phoneNumber:String) : Intent
    }

    data class State(
        val user: User,
        val isErrorPhone: Boolean = false,
        var isErrorName: Boolean = false,
        var isErrorPost: Boolean = false,
        var isErrorTelegram: Boolean = false
    )

    sealed interface Label {
        object ReturnedInProfile : Label
        object SavedChange : Label
        object Error: Label
    }
}