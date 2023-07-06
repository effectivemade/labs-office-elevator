package band.effective.office.elevator.ui.authorization

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.components.GoogleSignInButton
import band.effective.office.elevator.expects.showToast
import band.effective.office.elevator.ui.authorization.store.AuthorizationStore
import io.github.skeptick.libres.compose.painterResource
import band.effective.office.elevator.MainRes


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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(MainRes.image.effective_logo),
                    contentDescription = "Effective logo",
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = MainRes.string.company_name,
                    letterSpacing = 0.01.em,
                    fontSize = 40.sp,
                    color = Color(0xFF323E48) // TODO: Should add this color to Color.kt
//                fontFamily = FontFamily(Font( /* TODO font = museo_cyrl */))
                )
            }

            GoogleSignInButton(
                modifier = Modifier,//.align(alignment = Alignment.Center), MARK: removed align
                onClick = { onEvent(AuthorizationStore.Intent.SignInButtonClicked) })
        }
    }
}

