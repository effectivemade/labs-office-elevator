package band.effective.office.elevator.ui.profile.editProfile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ui.profile.editProfile.store.ProfileEditStore
import band.effective.office.elevator.ui.profile.mainProfile.ProfileComponent

@Composable
fun ProfileEditScreen(component: ProfileEditComponent){
    val user by component.user.collectAsState()

    LaunchedEffect(component){
        component.label.collect{ label->
            when(label){
                ProfileEditStore.Label.ReturnedInProfile -> component.onOutput(ProfileEditComponent.Output.OpenProfileFlow)
                ProfileEditStore.Label.SavedChange -> {}
            }
        }
    }

    ProfileEditScreenContent(
        username = user.username,
        post = user.post,
        telegram = user.telegram,
        phoneNumber = user.phoneNumber
    )
}

@Composable
private fun ProfileEditScreenContent(
    username: String?,
    post: String?,
    telegram: String?,
    phoneNumber: String?
) {
    Column (
        modifier = Modifier.fillMaxSize().padding(top = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        ProfileEditHeader()
    }
}

@Composable
fun ProfileEditHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
    ){

    }
}
