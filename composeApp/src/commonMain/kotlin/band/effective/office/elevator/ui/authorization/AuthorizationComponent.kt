package band.effective.office.elevator.ui.authorization

import band.effective.office.elevator.domain.models.UserData
import band.effective.office.elevator.expects.showToast
import band.effective.office.elevator.ui.authorization.authorization_google.AuthorizationGoogleComponent
import band.effective.office.elevator.ui.authorization.authorization_phone.AuthorizationPhoneComponent
import band.effective.office.elevator.ui.authorization.authorization_profile.AuthorizationProfileComponent
import band.effective.office.elevator.ui.authorization.authorization_telegram.AuthorizationTelegramComponent
import band.effective.office.elevator.ui.authorization.store.AuthorizationStore
import band.effective.office.elevator.ui.authorization.store.AuthorizationStoreFactory
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
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent

class AuthorizationComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val openContentFlow: () -> Unit
) :
    ComponentContext by componentContext, KoinComponent {

    private val validator: Validator = Validator()
    private val navigation = StackNavigation<AuthorizationComponent.Config>()
    private val userData: UserData = UserData()

    private fun changePhoneNumber(phoneNumber: String) {
        userData.phoneNumber = phoneNumber
    }

    private fun changeName(name: String) {
        userData.name = name
    }

    private fun changePost(post: String) {
        userData.post = post
    }

    private fun changeTelegramNick(telegramNick: String) {
        showToast(userData.phoneNumber)
        userData.telegramNick = telegramNick
    }

    private val authorizationStore =
        instanceKeeper.getStore {
            AuthorizationStoreFactory(
                storeFactory = storeFactory
            ).create()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<AuthorizationStore.State> = authorizationStore.stateFlow

    val label: Flow<AuthorizationStore.Label> = authorizationStore.labels

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
                    config.userData,
                    ::phoneAuthOutput,
                    ::changePhoneNumber
                )
            )

            is Config.ProfileAuth -> Child.ProfileAuthChild(
                AuthorizationProfileComponent(
                    componentContext,
                    storeFactory,
                    validator,
                    config.userData,
                    ::profileAuthOutput,
                    ::changeName,
                    ::changePost
                )
            )

            is Config.TelegramAuth -> Child.TelegramAuthChild(
                AuthorizationTelegramComponent(
                    componentContext,
                    storeFactory,
                    validator,
                    config.userData,
                    ::telegramAuthOutput,
                    ::changeTelegramNick
                )
            )
        }

    private fun googleAuthOutput(output: AuthorizationGoogleComponent.Output) {
        when (output) {
            is AuthorizationGoogleComponent.Output.OpenAuthorizationPhoneScreen -> navigation.replaceAll(
                Config.PhoneAuth(output.userData)
            )

            else -> {}
        }
    }

    private fun phoneAuthOutput(output: AuthorizationPhoneComponent.Output) {
        when (output) {
            is AuthorizationPhoneComponent.Output.OpenProfileScreen -> navigation.bringToFront(
                Config.ProfileAuth(output.userData)
            )

            AuthorizationPhoneComponent.Output.OpenGoogleScreen -> navigation.bringToFront(
                AuthorizationComponent.Config.GoogleAuth
            )
        }
    }

    private fun profileAuthOutput(output: AuthorizationProfileComponent.Output) {
        when (output) {
            is AuthorizationProfileComponent.Output.OpenPhoneScreen -> navigation.bringToFront(
                Config.PhoneAuth(output.userData)
            )

            is AuthorizationProfileComponent.Output.OpenTGScreen -> navigation.bringToFront(
                Config.TelegramAuth(output.userData)
            )
        }
    }

    private fun telegramAuthOutput(output: AuthorizationTelegramComponent.Output) {
        when (output) {
            is AuthorizationTelegramComponent.Output.OpenProfileScreen -> navigation.bringToFront(
                Config.ProfileAuth(output.userData)
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
        data class PhoneAuth(val userData: UserData) : Config()

        @Parcelize
        data class ProfileAuth(val userData: UserData) : Config()

        @Parcelize
        data class TelegramAuth(val userData: UserData) : Config()
    }
}