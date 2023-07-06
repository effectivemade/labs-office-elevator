package band.effective.office.elevator.ui.main

import band.effective.office.elevator.ui.elevator.ElevatorComponent
import band.effective.office.elevator.ui.profile.ProfileComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.StoreFactory

class MainComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val openAuthorizationFlow: () -> Unit
) :
    ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()
    private val stack = childStack(
        source = navigation,
        initialStack = { listOf(Config.Elevator) },
        childFactory = ::child,
    )
    val childStack: Value<ChildStack<*, Child>> = stack

    private fun child(config: Config, componentContext: ComponentContext): Child = when (config) {
        is Config.Elevator -> Child.Elevator(ElevatorComponent(componentContext, storeFactory))
        is Config.Profile -> Child.Profile(
            ProfileComponent(
                componentContext,
                storeFactory,
                ::profileOutput
            )
        )
    }

    fun onOutput(output: Output) {
        when (output) {
            Output.OpenElevatorTab -> navigation.bringToFront(Config.Elevator)
            Output.OpenProfileTab -> navigation.bringToFront(Config.Profile)
        }
    }

    private fun profileOutput(output: ProfileComponent.Output) {
        when (output) {
            ProfileComponent.Output.OpenAuthorizationFlow -> openAuthorizationFlow()
        }
    }

    sealed class Child {
        class Elevator(val component: ElevatorComponent) : Child()
        class Profile(val component: ProfileComponent) : Child()
    }

    private sealed interface Config : Parcelable {
        @Parcelize
        object Elevator : Config

        @Parcelize
        object Profile : Config
    }

    sealed class Output {
        object OpenProfileTab : Output()
        object OpenElevatorTab : Output()
    }
}
