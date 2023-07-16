package band.effective.office.elevator.ui.root

import band.effective.office.elevator.ui.authorization.authorization_google.AuthorizationGoogleComponent
import band.effective.office.elevator.ui.authorization.authorization_phone.AuthorizationPhoneComponent
import band.effective.office.elevator.ui.content.ContentComponent
import band.effective.office.elevator.ui.root.store.RootStore
import band.effective.office.elevator.ui.root.store.RootStoreImplFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow

class RootComponent internal constructor(
    componentContext: ComponentContext,
    private val storeFactory: DefaultStoreFactory,
    private val authorization: (ComponentContext, (AuthorizationGoogleComponent.Output) -> Unit) -> AuthorizationGoogleComponent,
//    region::Authorization tabs
    private val authorizationPhone: (ComponentContext, (AuthorizationPhoneComponent.Output) -> Unit) -> AuthorizationPhoneComponent,
//    endregion
    private val content: (ComponentContext, () -> Unit) -> ContentComponent,
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

    val childStack: Value<ChildStack<*, Child>> = stack

    private val rootStore =
        instanceKeeper.getStore {
            RootStoreImplFactory(
                storeFactory = storeFactory
            ).create()
        }

    val label: Flow<RootStore.Label> = rootStore.labels

    constructor(componentContext: ComponentContext, storeFactory: DefaultStoreFactory) : this(
        componentContext,
        storeFactory,
        authorization = { childContext, output ->
            AuthorizationGoogleComponent(childContext, storeFactory, output)
        },
        content = { childContext, onSignOut ->
            ContentComponent(
                childContext,
                storeFactory,
                openAuthorizationFlow = onSignOut
            )
        },
//        region::Authorization tabs
        authorizationPhone = { childContext, output ->
            AuthorizationPhoneComponent(
                childContext,
                storeFactory,
                output
            )
        }
//        endregion
        )

    private fun child(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            is Config.Authorization -> Child.AuthorizationChild(
                authorization(
                    componentContext,
                    ::onAuthorizationOutput
                )
            )

            is Config.Content -> Child.ContentChild(content(componentContext) {
                navigation.replaceAll(Config.Authorization)
            })

            Config.Undefined -> Child.Undefined

//            region::Authorization tabs
            is Config.AuthorizationPhone -> Child.AuthorizationPhoneChild(
                authorizationPhone(
                    componentContext,
                    ::onAuthorizationPhoneOutput
                )
            )
//            endregion
        }


    private fun onAuthorizationOutput(output: AuthorizationGoogleComponent.Output) {
        when (output) {
//            AuthorizationComponent.Output.OpenMainScreen -> navigation.replaceAll(Config.Content)
//            region::Authorization tabs
            AuthorizationGoogleComponent.Output.OpenAuthorizationPhoneScreen -> navigation.replaceAll(
                Config.AuthorizationPhone
            )
//            endregion
            else -> {}
        }
    }

//    region::Authorization tabs
    private fun onAuthorizationPhoneOutput(output: AuthorizationPhoneComponent.Output) {
        when (output) {
//            AuthorizationComponent.Output.OpenMainScreen -> navigation.replaceAll(Config.Content)
//            region::Authorization tabs
            AuthorizationPhoneComponent.Output.OpenProfileScreen -> navigation.replaceAll(
                Config.AuthorizationPhone
//            TODO()
            )
//            endregion
            else -> {}
        }
    }

//    endregion

    fun onOutput(output: Output) {
        when (output) {
            Output.OpenContent -> navigation.replaceAll(Config.Content)
            Output.OpenAuthorizationFlow -> navigation.replaceAll(Config.Authorization)
//            region::Authorization tabs
            Output.OpenAuthorizationPhone -> navigation.replaceAll(Config.AuthorizationPhone)
//            endregion
            else -> {}
        }
    }

    sealed interface Output {
        object OpenContent : Output
        object OpenAuthorizationFlow : Output

//        region::Authorization tabs
        object OpenAuthorizationPhone : Output
//        endregion
    }

    sealed class Child {
        object Undefined : Child()
        class AuthorizationChild(val component: AuthorizationGoogleComponent) : Child()

//        region::Authorization tabs
        class AuthorizationPhoneChild(val component: AuthorizationPhoneComponent) : Child()
//        endregion
        class ContentChild(val component: ContentComponent) : Child()
    }

    sealed class Config : Parcelable {
        @Parcelize
        object Undefined : Config()

        @Parcelize
        object Authorization : Config()

        @Parcelize
        object Content : Config()

//        region::Authorization tabs
        @Parcelize
        object AuthorizationPhone : Config()
//        endregion
    }
}
