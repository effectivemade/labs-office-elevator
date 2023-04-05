package band.effective.office.elevator.rpi

import band.effective.office.elevator.plugins.configureRouting
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 2105, host = "0.0.0.0") {
        configureRouting()
    }.start(wait = true)
}
