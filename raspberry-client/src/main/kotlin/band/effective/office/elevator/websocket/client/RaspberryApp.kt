package band.effective.office.elevator.websocket.client

import band.effective.office.elevator.websocket.client.plugins.configureRouting
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 50, host = "0.0.0.0") {
        configureRouting()
    }.start(wait = true)
}