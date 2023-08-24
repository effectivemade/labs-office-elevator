package band.effective.office.tablet

import band.effective.office.network.api.Collector
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ServerMessagingService() :
    FirebaseMessagingService(), KoinComponent {
    private val collector: Collector<String> by inject()

    init {
        listOf("workspace", "user", "booking").forEach { topic ->
            FirebaseMessaging.getInstance().subscribeToTopic(topic)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        collector.emit(message.from?.substringAfter("topics/") ?: "")
    }
}