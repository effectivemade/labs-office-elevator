package band.effective.office.elevator.websocket.server.client

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*

val ktorClient = HttpClient(CIO) {
    defaultRequest {
        url("0.0.0.0")
        port = 50
    }
}