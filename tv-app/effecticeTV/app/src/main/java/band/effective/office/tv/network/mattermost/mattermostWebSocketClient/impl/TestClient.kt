package band.effective.office.tv.network.mattermost.mattermostWebSocketClient.impl

import android.util.Log
import band.effective.office.tv.network.mattermost.mattermostWebSocketClient.MattermostWebSocketClient
import band.effective.office.tv.domain.botLogic.model.BotEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestClient: MattermostWebSocketClient {
    override fun connect() {
        Log.i("Mattermost Test Client", "connect")
    }

    override fun disconnect() {
        Log.i("Mattermost Test Client", "disconnect")
    }

    override fun subscribe(handler: (BotEvent) -> Unit) {
        val messages = listOf("Привет","Общий сбор","Мафия через 15 минут")

        CoroutineScope(Dispatchers.IO).launch {
            var currentMessageIndex = 0
            while (true){
                Log.i("Mattermost Test Client", messages[currentMessageIndex])
                handler(BotEvent(messages[currentMessageIndex]))
                currentMessageIndex = (currentMessageIndex + 1) % messages.size
                delay(10000)
            }
        }
    }
}