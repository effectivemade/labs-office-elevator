package band.effective.office.elevator.ui.authorization

import band.effective.office.elevator.ui.authorization.phone_authorization.AuthPhoneComponent
import band.effective.office.elevator.ui.authorization.profile_authorization.AuthProfileComponent
import band.effective.office.elevator.ui.authorization.store.AuthorizationStore
import band.effective.office.elevator.ui.authorization.store.AuthorizationStoreFactory
import band.effective.office.elevator.ui.authorization.tg_authorization.AuthTGComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow

class AuthorizationComponent internal constructor(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val authorization: (ComponentContext, (AuthPhoneComponent.Output) -> Unit) -> AuthPhoneComponent,
    private val output: (Output) -> Unit
) : ComponentContext by componentContext {
    init {
        Napier.base(DebugAntilog())
    }

    private val navigation = StackNavigation<Config>()

    private val stack = childStack(
        source = navigation,
        initialStack = { listOf(Config.Undefined) },
        handleBackButton = true,
        childFactory = ::child
    )

    private val authorizationStore =
        instanceKeeper.getStore {
            AuthorizationStoreFactory(
                storeFactory = storeFactory
            ).create()
        }

    val childStack: Value<ChildStack<*, Child>> = stack

    val label: Flow<AuthorizationStore.Label> = authorizationStore.labels

    constructor(
        componentContext: ComponentContext,
        storeFactory: DefaultStoreFactory,
        output: (Output) -> Unit
    ) : this(
        componentContext = componentContext,
        storeFactory = storeFactory,
        authorization = { childContext, output ->
            AuthPhoneComponent(childContext, storeFactory, output, "8-800-555-35-35")
        },
        output = {

        }
    )


    fun onEvent(event: AuthorizationStore.Intent) {
        authorizationStore.accept(event)
    }

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): Child =
        when (config) {
            is Config.PhoneAuthorization -> Child.AuthorizationPhoneChild(
                authorization(
                    componentContext,
                    ::phoneNumberOut
                )
            )

            is Config.ProfileAuthorization -> Child.AuthorizationProfileChild(
                AuthProfileComponent(componentContext, storeFactory, ::profileOut, name = "Имя фамилия", "музыкант")
            )

            Config.Undefined -> Child.Undefined
//            is Config.TGAuthorization -> Child.AuthorizationTGChild (
//                AuthTGComponent(componentContext, storeFactory, ::profileOut)
//                    )
            Config.TGAuthorization -> Child.AuthorizationTGChild(
                AuthTGComponent(componentContext, storeFactory, ::tgOut, nick = "@sdgfhgngd")
            )
        }

    private fun phoneNumberOut(out: AuthPhoneComponent.Output) {
        when (out) {
            AuthPhoneComponent.Output.OpenAuthProfileScreen -> navigation.replaceAll(Config.PhoneAuthorization)
            else -> {}
        }
    }

    private fun profileOut(out: AuthProfileComponent.Output) {
        when (out) {
            AuthProfileComponent.Output.OpenAuthTGScreen -> navigation.replaceAll(Config.TGAuthorization)
            else -> {}
        }
    }

    private fun tgOut(out: AuthTGComponent.Output) {
        when (out) {
            AuthTGComponent.Output.OpenMainScreen -> navigation.replaceAll(Config.Undefined)
            else -> {}
        }
    }

    fun onOutput(output: Output) {
        when (output) {
            Output.OpenPhoneScreen -> navigation.replaceAll(Config.PhoneAuthorization)
            Output.OpenProfileScreen -> navigation.replaceAll(Config.ProfileAuthorization)
            Output.OpenTGScreen -> navigation.replaceAll(Config.TGAuthorization)
        }
    }

    sealed class Output {
        object OpenPhoneScreen : Output()
        object OpenProfileScreen : Output()
        object OpenTGScreen : Output()
    }

    sealed class Child {
        object Undefined : Child()
        class AuthorizationPhoneChild(val component: AuthPhoneComponent) : Child()
        class AuthorizationProfileChild(val component: AuthProfileComponent) : Child()
        class AuthorizationTGChild(val component: AuthTGComponent) : Child()
    }

    sealed class Config : Parcelable {
        @Parcelize
        object Undefined : Config()

        @Parcelize
        object PhoneAuthorization : Config()

        @Parcelize
        object ProfileAuthorization : Config()

        @Parcelize
        object TGAuthorization : Config()
    }
}
