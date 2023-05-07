package band.effective.office.tv.screen.message.secondaryMessage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import band.effective.office.tv.screen.message.MessageScreen
import kotlinx.coroutines.delay

@Composable
fun SecondaryMessageScreen(viewModel: SecondaryMessageViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        val showMessageTime = 10000L
        delay(showMessageTime)
        viewModel.nextScreen()
    }
    MessageScreen(messagesList = state.messageList, uselessFact = state.uselessFact)
}