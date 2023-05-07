package band.effective.office.tv.screen.message.primaryMessage

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            modifier = Modifier.fillMaxSize().padding(30.dp),
            messagesList = state.messagesList,
            currentIndex = state.currentMessage
        )
    }
}