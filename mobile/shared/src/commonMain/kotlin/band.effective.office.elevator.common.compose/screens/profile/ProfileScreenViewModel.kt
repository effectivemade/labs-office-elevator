package band.effective.office.elevator.common.compose.screens.profile

import band.effective.office.elevator.common.compose.screens.login.GoogleAuthorization
import com.adeo.kviewmodel.BaseSharedViewModel
import kotlinx.coroutines.launch

class ProfileScreenViewModel : BaseSharedViewModel<ProfileState, ProfileAction, ProfileEvent>(
    ProfileState()
) {

    init {
        loadUserData()
    }

    private fun loadUserData() = viewModelScope.launch {
        val googleUserData = GoogleAuthorization.getAccount()
        this@ProfileScreenViewModel.viewState = ProfileState(
            imageUrl = googleUserData.imageUrl,
            username = googleUserData.username,
            email = googleUserData.email
        )
    }

    override fun obtainEvent(viewEvent: ProfileEvent) {
        when (viewEvent) {
            ProfileEvent.SignOut -> GoogleAuthorization.signOut()
        }
    }
}