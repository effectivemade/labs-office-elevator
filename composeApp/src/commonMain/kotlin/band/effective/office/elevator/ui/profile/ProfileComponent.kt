package band.effective.office.elevator.ui.profile

import band.effective.office.elevator.ui.profile.editProfile.ProfileEditComponent
import band.effective.office.elevator.ui.profile.mainProfile.MainProfileComponent
import band.effective.office.elevator.ui.profile.mainProfile.store.ProfileStore
import band.effective.office.elevator.ui.profile.mainProfile.store.ProfileStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class ProfileComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val openAuthorizationFlow: () -> Unit) :
    ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val stack = childStack(
        source = navigation,
        initialStack = { listOf(Config.MainProfile) },
        childFactory = ::child,
        handleBackButton = true
    )

    val childStack: Value<ChildStack<*,Child>> = stack

    private fun child(config: Config, componentContext: ComponentContext):Child =
        when(config){
            is Config.MainProfile -> Child.MainProfileChild(
                MainProfileComponent(
                    componentContext,
                    storeFactory,
                    ::mainProfileOutput,
                )
            )
            is Config.EditProfile -> Child.EditProfileChild(
                ProfileEditComponent(
                    componentContext,
                    storeFactory,
                    ::editProfileOutput,
                )
            )
        }

    private fun editProfileOutput(output: ProfileEditComponent.Output) {
        when(output){
            ProfileEditComponent.Output.OpenProfileFlow -> navigation.replaceAll(Config.MainProfile)
        }
    }

    private fun mainProfileOutput(output: MainProfileComponent.Output) {
        when(output){
            MainProfileComponent.Output.OpenAuthorizationFlow -> openAuthorizationFlow()
            MainProfileComponent.Output.OpenEditProfile -> navigation.bringToFront(Config.EditProfile)
        }
    }

    sealed class Child{
        class MainProfileChild(val component: MainProfileComponent) : Child()
        class EditProfileChild(val component: ProfileEditComponent): Child()
    }

    sealed class Config: Parcelable{
        @Parcelize
        object MainProfile: Config()

        @Parcelize
        object EditProfile: Config()
    }
}
