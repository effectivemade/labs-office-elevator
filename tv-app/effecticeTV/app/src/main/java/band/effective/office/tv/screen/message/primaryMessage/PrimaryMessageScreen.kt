package band.effective.office.tv.screen.message.primaryMessage

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import band.effective.office.tv.screen.message.MessageScreen

@Composable
fun PrimaryMessageScreen(
    viewModel: PrimaryMessageViewModel = hiltViewModel(),
    content: @Composable () -> Unit
) {
    val state by viewModel.state.collectAsState()
    if (state.isEmpty) {
        content()
    } else {
        MessageScreen(
            messagesList = state.messagesList,
            currentIndex = state.currentMessage
        )
    }
}