package office.effective.plugins

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import io.ktor.server.application.*
import io.ktor.server.request.*
import java.io.FileInputStream

/**
 * Notifies subscribers about user or workspace changes when sending a POST, PUT or DELETE request.
 * In the future, it is possible to perform operations with the response body.
 *
 * @author Daniil Zavyalov
 */
val FirebaseMessagingPlugin = createApplicationPlugin(name = "FirebaseMessagingPlugin") {
    val serviceAccount = FileInputStream("serviceAccountKey.json")
    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .build()

    val firebaseApp = FirebaseApp.initializeApp(options)
    val fcm = FirebaseMessaging.getInstance(firebaseApp)

    onCallRespond { call ->
        transformBody { data ->
            val httpMethod: String = call.request.httpMethod.value
            var topic = call.request.path().substring(1)
            topic = topic.split("/")[0]
            val statusCode = call.response.status()?.value ?: 200

            if (statusCode in 200..299 && httpMethod == "POST" || httpMethod == "PUT" || httpMethod == "DELETE") {
                val msg: Message = Message.builder()
                    .setTopic(topic)
                    .putData("httpMethod", httpMethod)
                    .build()
                fcm.send(msg)
            }
            data
        }
    }
}