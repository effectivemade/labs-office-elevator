package office.effective.plugins

import io.ktor.server.application.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import office.effective.common.utils.ListenToNotifyMessage

fun Application.configureNotification() {
    CoroutineScope(Job()).launch {
        ListenToNotifyMessage.listenToNotifyMessage()
    }
}