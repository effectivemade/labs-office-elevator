package band.effective.office.elevator.websocket.server.client

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*

val ktorClient = HttpClient(CIO) {
    defaultRequest {
        url("http://4062-188-162-86-244.eu.ngrok.io")
        port = 4040
    }
    install(HttpTimeout){
        this.requestTimeoutMillis = 100000000000000
        this.connectTimeoutMillis = 100000000000000
        this.socketTimeoutMillis = 100000000000000
    }
}