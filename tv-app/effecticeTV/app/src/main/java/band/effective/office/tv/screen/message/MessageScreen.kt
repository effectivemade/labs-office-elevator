package band.effective.office.tv.screen.message

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tv.domain.model.message.BotMessage
import band.effective.office.tv.screen.message.component.EmptyMessageScreen
import band.effective.office.tv.screen.message.component.MoreMessagesScreen
import band.effective.office.tv.screen.message.component.OneMessageScreen

@Composable
fun MessageScreen(
    modifier: Modifier = Modifier,
    messagesList: List<BotMessage> = listOf(),
    currentIndex: Int = 0,
    uselessFact: String = ""
) {
    when (messagesList.size) {
        0 -> EmptyMessageScreen(
            modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp), uselessFact)
        1 -> OneMessageScreen(modifier = modifier, message = messagesList[0])
        else -> MoreMessagesScreen(
            modifier = modifier,
            messagesList = messagesList,
            currentIndex = currentIndex
        )
    }
}