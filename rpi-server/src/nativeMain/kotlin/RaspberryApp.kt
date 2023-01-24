import band.effective.office.elevator.plugins.configureRouting
import io.ktor.server.cio.*
import io.ktor.server.engine.*

fun main() {
    embeddedServer(CIO, port = 50, host = "0.0.0.0") {
        configureRouting()
    }.start(wait = true)
}
