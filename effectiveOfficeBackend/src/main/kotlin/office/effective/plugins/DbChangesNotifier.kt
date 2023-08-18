package office.effective.plugins

import io.ktor.server.application.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import office.effective.common.notifications.DbNotifier

fun Application.configureNotification() {
    val dbNotifier: DbNotifier = DbNotifier()
    CoroutineScope(Job()).launch {
        dbNotifier.listenToNotifyMessage()
    }
}