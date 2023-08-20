package office.effective.common.notifications

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.dsl.module

val firebaseDiModule  = module(createdAtStart = true) {
    val serviceAccountKeyPath: String? = System.getenv("SERVICE_ACCOUNT_KEY_PATH")

    single<FirebaseOptions> {
        val keyStream = javaClass.classLoader.getResourceAsStream(serviceAccountKeyPath)
            ?: throw Exception("Service account key json not found")
        keyStream.use { stream ->
            FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(stream))
                .build()
        }
    }
    single<FirebaseApp> {
        FirebaseApp.initializeApp(get<FirebaseOptions>())
    }
    single<FirebaseMessaging> {
        FirebaseMessaging.getInstance(get<FirebaseApp>())
    }
    single {
        NotificationSender(get<FirebaseMessaging>())
    }
}
