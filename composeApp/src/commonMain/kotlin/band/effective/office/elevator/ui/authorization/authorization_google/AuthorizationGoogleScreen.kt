package band.effective.office.elevator.ui.authorization.authorization_google

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
import band.effective.office.elevator.ui.authorization.authorization_google.store.AuthorizationGoogleStore


@Composable
fun AuthorizationGoogleScreen(component: AuthorizationGoogleComponent) {

    LaunchedEffect(component) {
        component.label.collect { label ->
            when (label) {
                is AuthorizationGoogleStore.Label.AuthorizationFailure -> showToast(label.message)
                AuthorizationGoogleStore.Label.AuthorizationSuccess -> component.onOutput(
                    AuthorizationGoogleComponent.Output.OpenAuthorizationPhoneScreen
                )
            }
        }
    }

    AuthorizationGoogleScreenContent(onEvent = component::onEvent)
}


@Composable
private fun AuthorizationGoogleScreenContent(onEvent: (AuthorizationGoogleStore.Intent) -> Unit) {
    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        GoogleSignInButton(
            modifier = Modifier.align(Alignment.Center),
            onClick = { onEvent(AuthorizationGoogleStore.Intent.SignInButtonClicked) })
    }
}

