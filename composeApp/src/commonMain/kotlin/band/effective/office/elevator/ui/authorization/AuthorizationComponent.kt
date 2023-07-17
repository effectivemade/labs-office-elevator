package band.effective.office.elevator.ui.authorization

import band.effective.office.elevator.ui.authorization.authorization_google.AuthorizationGoogleComponent
import band.effective.office.elevator.ui.authorization.authorization_phone.AuthorizationPhoneComponent
import band.effective.office.elevator.ui.profile.ProfileComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.StoreFactory

class AuthorizationComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val openAuthorizationFlow: () -> Unit
) :
    ComponentContext by componentContext {

    private val navigation = StackNavigation<AuthorizationComponent.Config>()

    private val stack = childStack(
        source = navigation,
        initialStack = { listOf(AuthorizationComponent.Config.GoogleAuth) },
        childFactory = ::child,
        handleBackButton = true
    )

    val childStack: Value<ChildStack<*, AuthorizationComponent.Child>> = stack

    private fun child(
        config: AuthorizationComponent.Config,
        componentContext: ComponentContext
    ): AuthorizationComponent.Child =
        when (config) {
            is AuthorizationComponent.Config.GoogleAuth -> AuthorizationComponent.Child.GoogleAuthChild(
                AuthorizationGoogleComponent(
                    componentContext,
                    storeFactory,
                    ::googleAuthOutput
                )
            )

            is AuthorizationComponent.Config.PhoneAuth -> AuthorizationComponent.Child.PhoneAuthChild(
                AuthorizationPhoneComponent(
                    componentContext,
                    storeFactory,
                    ::phoneAuthOutput
                )
            )
        }

    //    region::Output
    private fun googleAuthOutput(output: AuthorizationGoogleComponent.Output) {
        when (output) {
            AuthorizationGoogleComponent.Output.OpenAuthorizationPhoneScreen -> navigation.replaceAll(
                Config.PhoneAuth
            )

            else -> {}
        }
    }

    private fun phoneAuthOutput(output: AuthorizationPhoneComponent.Output) {
        when (output) {
            AuthorizationPhoneComponent.Output.OpenProfileScreen -> navigation.bringToFront(
                AuthorizationComponent.Config.PhoneAuth
            )
            AuthorizationPhoneComponent.Output.OpenGoogleScreen -> navigation.bringToFront(
                AuthorizationComponent.Config.GoogleAuth
            )
        }
    }
//    endregion

    sealed class Child {
        class GoogleAuthChild(val component: AuthorizationGoogleComponent) : Child()
        class PhoneAuthChild(val component: AuthorizationPhoneComponent) : Child()
    }

    sealed class Config : Parcelable {
        @Parcelize
        object GoogleAuth : Config()

        @Parcelize
        object PhoneAuth : Config()
    }
}