package band.effective.office.elevator.ui.profile.editProfile.store

import band.effective.office.elevator.domain.models.User
import com.arkivanov.mvikotlin.core.store.Store

interface ProfileEditStore : Store<ProfileEditStore.Intent, User, ProfileEditStore.Label>{
    sealed interface Intent {
        object BackInProfileClicked : Intent
        object SaveChangeClicked : Intent
    }

    sealed interface Label {
        object ReturnedInProfile : Label
        object SavedChange : Label
    }
}