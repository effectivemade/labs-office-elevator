package band.effective.office.elevator.ui.profile

import androidx.compose.runtime.Composable
import band.effective.office.elevator.ui.profile.editProfile.ProfileEditScreen
import band.effective.office.elevator.ui.profile.mainProfile.ProfileScreen
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children

@Composable
fun Profile (component: ProfileComponent){
    Children(
        stack = component.childStack
    ){
        when(val child = it.instance){
            is ProfileComponent.Child.MainProfileChild -> ProfileScreen(child.component)
            is ProfileComponent.Child.EditProfileChild -> ProfileEditScreen(child.component)
        }
    }
}