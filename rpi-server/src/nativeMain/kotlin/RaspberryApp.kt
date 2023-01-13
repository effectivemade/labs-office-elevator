import band.effective.office.elevator.plugins.configureRouting
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer

fun main() {
    println("Started!!!")
    embeddedServer(CIO, port = 50, host = "0.0.0.0") {
        configureRouting()
    }.start(wait = true)
}
