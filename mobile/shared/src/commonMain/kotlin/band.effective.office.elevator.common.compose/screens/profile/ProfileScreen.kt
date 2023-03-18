package band.effective.office.elevator.common.compose.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
internal fun ProfileScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text("Profile Screen", modifier = Modifier.align(Alignment.Center))
    }
}