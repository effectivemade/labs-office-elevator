package band.effective.office.tv.screen.message.secondaryMessage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import band.effective.office.tv.screen.message.MessageScreen

@Composable
fun SecondaryMessageScreen(viewModel: SecondaryMessageViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    MessageScreen(
        messagesList = state.messageList,
        currentIndex = state.currentIndex,
        uselessFact = state.uselessFact
    )
}