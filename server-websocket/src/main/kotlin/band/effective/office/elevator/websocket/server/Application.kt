package band.effective.office.elevator.websocket.server

import band.effective.office.elevator.websocket.server.plugins.configureRouting
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8000, host = "localhost") {
        configureRouting()
    }.start(wait = true)
}
