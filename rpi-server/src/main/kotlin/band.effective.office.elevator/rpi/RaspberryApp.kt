package band.effective.office.elevator.rpi

import band.effective.office.elevator.plugins.configureRouting
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(Netty, port = 2105, host = "0.0.0.0") {
        configureRouting()
    }.start(wait = true)
}
