package band.effective.office.elevator.ui.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import band.effective.office.elevator.ui.authorization.AuthorizationScreen
import band.effective.office.elevator.ui.content.Content
import band.effective.office.elevator.ui.root.store.RootStore
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation

@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {

    LaunchedEffect(component) {
        component.label.collect { label ->
            when (label) {
                RootStore.Label.UserAlreadySigned -> component.onOutput(
                    RootComponent.Output.OpenContent
                )

                RootStore.Label.UserNotSigned -> component.onOutput(RootComponent.Output.OpenAuthorizationFlow)
            }
        }
    }

    Children(
        stack = component.childStack,
        modifier = modifier,
        animation = stackAnimation(fade() + scale()),
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.AuthorizationChild -> AuthorizationScreen(child.component)
            is RootComponent.Child.ContentChild -> Content(child.component)
            RootComponent.Child.Undefined -> {
                // Wait until fetch Google account if user signed in previously
            }
        }
    }
}
