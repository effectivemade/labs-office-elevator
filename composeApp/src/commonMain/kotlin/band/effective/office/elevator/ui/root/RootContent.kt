package band.effective.office.elevator.ui.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            println("was label")
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
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
