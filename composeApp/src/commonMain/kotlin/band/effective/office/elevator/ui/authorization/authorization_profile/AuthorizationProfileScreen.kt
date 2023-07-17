package band.effective.office.elevator.ui.authorization.authorization_profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import band.effective.office.elevator.ui.authorization.authorization_profile.store.AuthorizationProfileStore

@Composable
fun AuthorizationProfileScreen(component: AuthorizationProfileComponent) {
    LaunchedEffect(component) {
        component.label.collect { label ->
            when (label) {
                AuthorizationProfileStore.Label.AuthorizationProfileFailure -> TODO()
                AuthorizationProfileStore.Label.AuthorizationProfileSuccess -> TODO()
                AuthorizationProfileStore.Label.OpenTGAuthorization -> component.onOutput(
                    AuthorizationProfileComponent.Output.OpenTGScreen
                )

                AuthorizationProfileStore.Label.ReturnInPhoneAuthorization -> component.onOutput(
                    AuthorizationProfileComponent.Output.OpenPhoneScreen
                )
            }
        }
    }

    AuthorizationProfileComponent(onEvent = component::onEvent)
}

@Composable
fun AuthorizationProfileComponent(onEvent: (AuthorizationProfileStore.Intent) -> Unit) {

}