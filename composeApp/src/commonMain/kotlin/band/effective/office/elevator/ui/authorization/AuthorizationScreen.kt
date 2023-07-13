package band.effective.office.elevator.ui.authorization

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.components.GoogleSignInButton
import band.effective.office.elevator.expects.showToast
import band.effective.office.elevator.ui.authorization.store.AuthorizationStore
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState


@Composable
fun AuthorizationScreen(component: AuthorizationComponent) {

    LaunchedEffect(component) {
        component.label.collect { label ->
            when (label) {
                is AuthorizationStore.Label.AuthorizationFailure -> showToast(label.message)

                AuthorizationStore.Label.AuthorizationSuccess -> component.onOutput(
                    AuthorizationComponent.Output.OpenPhoneScreen
                )

                AuthorizationStore.Label.Skip -> component.onOutput(
                    AuthorizationComponent.Output.OpenPhoneScreen
                )
            }
        }
    }

    AuthorizationScreenContent(onEvent = component::onEvent, component)
}


@Composable
private fun AuthorizationScreenContent(onEvent: (AuthorizationStore.Intent) -> Unit, component : AuthorizationComponent) {
    val childStack by component.childStack.subscribeAsState()
    val activeComponent = childStack.active.instance

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Children(
            stack = component.childStack,
            modifier = Modifier,
        ) {
            when (val child = it.instance) {
                is AuthorizationComponent.Child.AuthorizationPhoneChild -> TODO()
                is AuthorizationComponent.Child.AuthorizationProfileChild -> TODO()
                is AuthorizationComponent.Child.AuthorizationTGChild -> TODO()
                AuthorizationComponent.Child.Undefined -> TODO()
            }
        }
        GoogleSignInButton(
            modifier = Modifier.align(Alignment.Center),
            onClick = { onEvent(AuthorizationStore.Intent.Skip) })
    }
}

