package band.effective.office.tv.screen.message.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import band.effective.office.tv.domain.model.message.BotMessage

@Composable
fun OneMessageScreen(modifier: Modifier, message: BotMessage) {
    Text(message.text)
}