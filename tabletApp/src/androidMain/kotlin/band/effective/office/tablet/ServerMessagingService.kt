package band.effective.office.tablet

import android.util.Log
import band.effective.office.network.api.Collector
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class ServerMessagingService() :
    FirebaseMessagingService(), KoinComponent {
    private val collector: Collector<String> by inject()
    private val topics: List<String> by inject(qualifier = named("FireBaseTopics"))

    init {
        Log.i("Firebase", "Firebase started")
        topics.forEach { topic ->
            FirebaseMessaging.getInstance().subscribeToTopic(topic)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.i("ReceivedMessage", message.toString())
        collector.emit(message.from?.substringAfter("topics/")?.replace("-test", "") ?: "")
    }
}