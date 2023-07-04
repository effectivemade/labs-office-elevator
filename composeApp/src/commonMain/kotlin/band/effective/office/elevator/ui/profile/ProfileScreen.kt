package band.effective.office.elevator.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.MR
import band.effective.office.elevator.common.compose.components.GrayText
import band.effective.office.elevator.lightGray
import band.effective.office.elevator.ui.profile.store.ProfileStore
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.rememberAsyncImagePainter
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun ProfileScreen(component: ProfileComponent) {
    val state by component.state.collectAsState()

    LaunchedEffect(component) {
        component.label.collect { label ->
            when (label) {
                ProfileStore.Label.OnSignedOut -> component.onOutput(ProfileComponent.Output.OpenAuthorizationFlow)
            }
        }
    }

    ProfileScreenContent(
        imageUrl = state.imageUrl,
        username = state.username,
        email = state.email,
        onSignOut = { component.onEvent(ProfileStore.Intent.SignOutClicked) }
    )
}

@Composable
internal fun ProfileScreenContent(
    imageUrl: String?,
    username: String?,
    email: String?,
    onSignOut: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.weight(.2f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(text = stringResource(MR.strings.profile), style = MaterialTheme.typography.h3)
            Spacer(modifier = Modifier.weight(.1f))
            IconButton(onClick = onSignOut) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.ExitToApp,
                        contentDescription = null,
                        tint = MaterialTheme.colors.secondary
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        imageUrl?.let { url ->
            val request = remember(url) {
                ImageRequest {
                    data(url)
                }
            }
            val painter = rememberAsyncImagePainter(request)

            Box {
                Surface(
                    modifier = Modifier.size(120.dp).align(Alignment.Center),
                    shape = CircleShape,
                    color = lightGray
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize().align(Alignment.Center),
                        painter = painter,
                        contentDescription = null,
                    )
                }

            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        username?.let {
            Text(it, style = MaterialTheme.typography.h5)
        }
        Spacer(modifier = Modifier.height(8.dp))
        email?.let {
            GrayText(text = it, style = MaterialTheme.typography.subtitle1)
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}