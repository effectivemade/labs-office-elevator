package band.effective.office.elevator.common.compose.network

import band.effective.office.elevator.common.compose.helpers.BaseUrl
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*

val ktorClient: HttpClient = HttpClient() {
    defaultRequest {
        host = BaseUrl
        port = 80
    }
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Napier.d(message = message)
            }
        }
    }
    install(HttpTimeout){
        this.requestTimeoutMillis = 100000000000000
        this.connectTimeoutMillis = 100000000000000
        this.socketTimeoutMillis = 100000000000000
    }
}