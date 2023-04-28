package band.effective.office.tv.screen.message.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import band.effective.office.tv.domain.model.message.BotMessage

@Composable
fun MoreMessagesScreen(modifier: Modifier, messagesList: List<BotMessage>) {
    Column() {
        for (message in messagesList) {
            Text(message.text)
        }
    }
}