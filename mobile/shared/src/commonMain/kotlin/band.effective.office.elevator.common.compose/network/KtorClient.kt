package band.effective.office.elevator.common.compose.network

import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*

val ktorClient: HttpClient = HttpClient() {
    defaultRequest {
        host = "51.250.11.188"
        port = 2105
    }
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Napier.d(message = message)
            }
        }
    }
}