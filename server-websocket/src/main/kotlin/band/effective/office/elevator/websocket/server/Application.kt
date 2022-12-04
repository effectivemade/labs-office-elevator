package band.effective.office.elevator.websocket.server

import band.effective.office.elevator.websocket.server.utils.EncryptionUtils
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import java.time.Duration
import java.time.LocalDateTime

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    val isCanSent = MutableStateFlow(false)
    val raspberryMessage = MutableStateFlow("")
    routing {

        get("/office-elevator") {
            isCanSent.emit(true)
            call.respondText { "Elevator requested" }
            delay(5000)
            isCanSent.emit(false)
        }

        webSocket("/elevator-connection") {
            isCanSent.collect { value: Boolean ->
                if (value) {
                    val currentTime = LocalDateTime.now().toString()
                    send(currentTime)
                    val hashCode = EncryptionUtils.encrypt(currentTime)
                    send(hashCode)
                    val requestResult = (incoming.receive() as? Frame.Text?)?.readText()
                    println("Raspberry answer: $requestResult")
                    raspberryMessage.value = requestResult ?: "Undefined message"
                    delay(5000)
                    raspberryMessage.value = ""
                }
            }
        }

        webSocket("/elevator-state") {
            raspberryMessage.collect { value ->
                send(value)
            }
        }
    }
}
