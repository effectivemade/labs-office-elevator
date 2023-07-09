package band.effective.office.elevator.ui.authorization

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.components.GoogleSignInButton
import band.effective.office.elevator.expects.showToast
import band.effective.office.elevator.ui.authorization.store.AuthorizationStore
import dev.icerock.moko.resources.desc.StringDesc


@Composable
fun AuthorizationScreen(component: AuthorizationComponent) {

    LaunchedEffect(component) {
        component.label.collect { label ->
            when (label) {
                is AuthorizationStore.Label.AuthorizationFailure -> showToast(label.message)
                AuthorizationStore.Label.AuthorizationSuccess -> component.onOutput(
                    AuthorizationComponent.Output.OpenMainScreen
                )
            }
        }
    }

    AuthorizationScreenContent(onEvent = component::onEvent)
}


@Composable
private fun AuthorizationScreenContent(onEvent: (AuthorizationStore.Intent) -> Unit) {
    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        GoogleSignInButton(
            modifier = Modifier.align(Alignment.Center),
            onClick = { onEvent(AuthorizationStore.Intent.SignInButtonClicked) })
    }
}

