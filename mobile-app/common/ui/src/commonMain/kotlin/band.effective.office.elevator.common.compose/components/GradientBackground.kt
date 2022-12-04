package band.effective.office.elevator.common.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GradientBackground() {
    Box(modifier = Modifier.fillMaxSize()) {
        LocalImage("component", modifier = Modifier.fillMaxSize(), null)
    }
}