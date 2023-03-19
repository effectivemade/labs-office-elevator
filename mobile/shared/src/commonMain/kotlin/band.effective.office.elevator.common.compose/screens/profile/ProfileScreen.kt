package band.effective.office.elevator.common.compose.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.common.compose.components.GrayText
import band.effective.office.elevator.common.compose.expects.ImageVector
import com.adeo.kviewmodel.compose.ViewModel
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.rememberAsyncImagePainter

@Composable
internal fun ProfileScreen(onSignOut: () -> Unit) {
    ViewModel(factory = { ProfileScreenViewModel() }) { viewModel ->

        val state by viewModel.viewStates().collectAsState()

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
                Text(text = "Profile", style = MaterialTheme.typography.h3)
                Spacer(modifier = Modifier.weight(.1f))
                IconButton(
                    onClick = {
                        viewModel.obtainEvent(ProfileEvent.SignOut)
                        onSignOut()
                    }
                ) {
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
            state.imageUrl?.let { url ->
                val request = remember(url) {
                    ImageRequest {
                        data(url)
                    }
                }
                val painter = rememberAsyncImagePainter(request)

                Box {
                    Surface(
                        modifier = Modifier.size(120.dp).align(Alignment.Center),
                        shape = CircleShape
                    ) {
                        Image(
                            modifier = Modifier.fillMaxSize().align(Alignment.Center),
                            painter = painter,
                            contentDescription = null,
                        )
                    }
                    ImageVector("avatar_border", modifier = Modifier)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            state.username?.let {
                Text(it, style = MaterialTheme.typography.h5)
            }
            Spacer(modifier = Modifier.height(8.dp))
            state.email?.let {
                GrayText(text = it, style = MaterialTheme.typography.subtitle1)
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}