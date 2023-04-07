package band.effective.office.elevator.common.compose.screens.profile

import band.effective.office.elevator.common.compose.screens.login.GoogleAuthorization
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.launch

internal class ProfileScreenViewModel : StateScreenModel<ProfileState>(initialState = ProfileState()) {

    init {
        loadUserData()
    }

    private fun loadUserData() = coroutineScope.launch {
        val googleUserData = GoogleAuthorization.getAccount()
        mutableState.value = ProfileState(
            imageUrl = googleUserData.imageUrl,
            username = googleUserData.username,
            email = googleUserData.email
        )
    }

    fun sendEvent(viewEvent: ProfileEvent) {
        when (viewEvent) {
            ProfileEvent.SignOut -> GoogleAuthorization.signOut()
        }
    }
}