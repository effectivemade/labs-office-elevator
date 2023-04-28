package band.effective.office.tv.screen.message.primaryMessage

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import band.effective.office.tv.screen.message.component.OneMessageScreen

@Composable
fun PrimaryMessageScreen(
    viewModel: PrimaryMessageViewModel = hiltViewModel(),
    content: @Composable () -> Unit
) {
    val state by viewModel.state.collectAsState()
    if (state.isEmpty) {
        content()
    } else {
        OneMessageScreen(modifier = Modifier.fillMaxSize(), message = state.currentMessage)
    }
}