package band.effective.office.elevator.ui.root

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ui.authorization.AuthorizationScreen
import band.effective.office.elevator.ui.content.Content
import band.effective.office.elevator.ui.root.store.RootStore
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {

    LaunchedEffect(component) {
        component.label.collect { label ->
            println("was label")
            when (label) {
                RootStore.Label.UserAlreadySigned -> component.onOutput(
                    RootComponent.Output.OpenContent
                )

                RootStore.Label.UserNotSigned -> component.onOutput(RootComponent.Output.OpenAuthorizationFlow)
            }
        }
    }
    val slot by component.slot.subscribeAsState()
    Box(
        modifier = modifier,
        //animation = stackAnimation(fade() + scale()),
    ) {
        slot.child?.instance?.also { child ->
            AnimatedChild({ child is RootComponent.Child.AuthorizationChild }) {
                (child as? RootComponent.Child.AuthorizationChild)?.run {
                    AuthorizationScreen(child.component)
                }
            }
            AnimatedChild({ child is RootComponent.Child.ContentChild }) {
                (child as? RootComponent.Child.ContentChild)?.run {
                    Content(child.component)
                }
            }
            AnimatedChild({ child is RootComponent.Child.Undefined }) {
                (child as? RootComponent.Child.AuthorizationChild)?.run {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(80.dp)
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AnimatedChild(visible: () -> Boolean, content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = visible(),
        enter = fadeIn(spring(stiffness = Spring.StiffnessHigh)) + scaleIn(spring(stiffness = Spring.StiffnessHigh)),
        exit = fadeOut() + scaleOut()
    ) {
        content()
    }
}
