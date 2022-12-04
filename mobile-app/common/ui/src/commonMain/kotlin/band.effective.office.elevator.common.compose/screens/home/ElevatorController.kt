package band.effective.office.elevator.common.compose.screens.home

import band.effective.office.elevator.common.compose.helpers.BaseUrl
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

object ElevatorController {

    private val client: HttpClient = HttpClient(CIO) {
        defaultRequest {
            url("http://$BaseUrl")
            port = 8080
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.i(message, null, "HTTP Client")
                }
            }
        }
        install(WebSockets) {
            pingInterval = 20_000
        }
    }
    private val mutableWebSocketMessage = MutableStateFlow("")
    val webSocketMessage = mutableWebSocketMessage.asStateFlow()
    private val scope = CoroutineScope(Dispatchers.Default)

    init {
        listenWebSocket()
    }

    private fun listenWebSocket() = scope.launch {
        try {
            client.webSocket(
                method = HttpMethod.Get,
                host = BaseUrl,
                port = 8080,
                path = "/elevator-state"
            ) {
                while (true) {
                    val message = (incoming.receive() as? Frame.Text)?.readText()
                    Napier.i(message = "message: $message", tag = "WebHook info")
                    if (message != null) {
                        mutableWebSocketMessage.emit(message)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            mutableWebSocketMessage.value = "Unable to connect"
            client.close()
        }
    }

    suspend fun callElevator(): Result<HttpResponse> {
        Napier.e { "Call elevator ..." }
        return runCatching {
            client.get("office-elevator")
        }
    }
}