package band.effective.office.elevator.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import band.effective.office.elevator.components.TabNavigationItem
import band.effective.office.elevator.navigation.ElevatorTab
import band.effective.office.elevator.navigation.ProfileTab
import band.effective.office.elevator.ui.main_screem_content.MainScreenContent
import band.effective.office.elevator.ui.profile.ProfileScreen
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.Direction
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.StackAnimator
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.isEnter
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

@Composable
fun MainScreen(component: MainComponent) {
    val childStack by component.childStack.subscribeAsState()
    val activeComponent = childStack.active.instance
    Scaffold(
        modifier = Modifier,
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                Children(
                    stack = component.childStack,
                    modifier = Modifier,
                    animation = tabAnimation()
                ) {
                    when (val child = it.instance) {
                        is MainComponent.Child.Elevator -> MainScreenContent(child.component)
                        is MainComponent.Child.Profile -> ProfileScreen(child.component)
                    }
                }
            }
        },
        bottomBar = {
            Box(modifier = Modifier.background(MaterialTheme.colors.primary)) {
                BottomNavigation {
                    TabNavigationItem(
                        tab = ElevatorTab,
                        selected = activeComponent is MainComponent.Child.Elevator
                    ) {
                        component.onOutput(MainComponent.Output.OpenElevatorTab)
                    }
                    TabNavigationItem(
                        tab = ProfileTab,
                        selected = activeComponent is MainComponent.Child.Profile
                    ) {
                        component.onOutput(MainComponent.Output.OpenProfileTab)
                    }
                }
            }
        }
    )
}

private val MainComponent.Child.index: Int
    get() =
        when (this) {
            is MainComponent.Child.Elevator -> 0
            is MainComponent.Child.Profile -> 1
        }

@Composable
private fun tabAnimation(): StackAnimation<Any, MainComponent.Child> =
    stackAnimation { child, otherChild, direction ->
        val index = child.instance.index
        val otherIndex = otherChild.instance.index
        val anim = slide()
        if ((index > otherIndex) == direction.isEnter) anim else anim.flipSide()
    }

private fun StackAnimator.flipSide(): StackAnimator =
    StackAnimator { direction, isInitial, onFinished, content ->
        invoke(
            direction = direction.flipSide(),
            isInitial = isInitial,
            onFinished = onFinished,
            content = content,
        )
    }

@Suppress("OPT_IN_USAGE")
private fun Direction.flipSide(): Direction =
    when (this) {
        Direction.ENTER_FRONT -> Direction.ENTER_BACK
        Direction.EXIT_FRONT -> Direction.EXIT_BACK
        Direction.ENTER_BACK -> Direction.ENTER_FRONT
        Direction.EXIT_BACK -> Direction.EXIT_FRONT
    }

