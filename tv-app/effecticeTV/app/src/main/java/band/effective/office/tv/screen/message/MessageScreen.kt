package band.effective.office.tv.screen.message

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MessageScreen(
    viewModel: MessageScreenViewModel = hiltViewModel(),
    content: @Composable BoxScope.() -> Unit
) {
    val state by viewModel.state.collectAsState()
    Box(Modifier.fillMaxSize()) {
        content()
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {
            Text(state)
        }
    }
}