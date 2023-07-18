package band.effective.office.elevator.ui.authorization

import band.effective.office.elevator.ui.authorization.authorization_google.AuthorizationGoogleComponent
import band.effective.office.elevator.ui.authorization.authorization_phone.AuthorizationPhoneComponent
import band.effective.office.elevator.ui.authorization.authorization_profile.AuthorizationProfileComponent
import band.effective.office.elevator.ui.authorization.authorization_telegram.AuthorizationTelegramComponent
import band.effective.office.elevator.ui.models.validator.Validator
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
    private val openContentFlow: () -> Unit
) :
    ComponentContext by componentContext {

    private val validator: Validator = Validator()
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
            is Config.GoogleAuth -> Child.GoogleAuthChild(
                AuthorizationGoogleComponent(
                    componentContext,
                    storeFactory,
                    ::googleAuthOutput
                )
            )

            is Config.PhoneAuth -> Child.PhoneAuthChild(
                AuthorizationPhoneComponent(
                    componentContext,
                    storeFactory,
                    validator,
                    ::phoneAuthOutput
                )
            )

            is Config.ProfileAuth -> Child.ProfileAuthChild(
                AuthorizationProfileComponent(
                    componentContext,
                    storeFactory,
                    validator,
                    ::profileAuthOutput
                )
            )

            is Config.TelegramAuth -> Child.TelegramAuthChild(
                AuthorizationTelegramComponent(
                    componentContext,
                    storeFactory,
                    validator,
                    ::telegramAuthOutput
                )
            )
        }

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
                AuthorizationComponent.Config.ProfileAuth
            )

            AuthorizationPhoneComponent.Output.OpenGoogleScreen -> navigation.bringToFront(
                AuthorizationComponent.Config.GoogleAuth
            )
        }
    }

    private fun profileAuthOutput(output: AuthorizationProfileComponent.Output) {
        when (output) {
            AuthorizationProfileComponent.Output.OpenPhoneScreen -> navigation.bringToFront(
                Config.PhoneAuth
            )

            AuthorizationProfileComponent.Output.OpenTGScreen -> navigation.bringToFront(
                Config.TelegramAuth
            )
        }
    }

    private fun telegramAuthOutput(output: AuthorizationTelegramComponent.Output) {
        when (output) {
            AuthorizationTelegramComponent.Output.OpenProfileScreen -> navigation.bringToFront(
                Config.ProfileAuth
            )

            AuthorizationTelegramComponent.Output.OpenContentFlow -> openContentFlow()
        }
    }

    sealed class Child {
        class GoogleAuthChild(val component: AuthorizationGoogleComponent) : Child()
        class PhoneAuthChild(val component: AuthorizationPhoneComponent) : Child()
        class ProfileAuthChild(val component: AuthorizationProfileComponent) : Child()
        class TelegramAuthChild(val component: AuthorizationTelegramComponent) : Child()
    }

    sealed class Config : Parcelable {
        @Parcelize
        object GoogleAuth : Config()

        @Parcelize
        object PhoneAuth : Config()

        @Parcelize
        object ProfileAuth : Config()

        @Parcelize
        object TelegramAuth : Config()
    }
}