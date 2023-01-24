package band.effective.office.elevator.common.compose.network

import band.effective.office.elevator.common.compose.helpers.BaseUrl
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*

val ktorClient: HttpClient = HttpClient() {
    defaultRequest {
        // host = "167.99.5.228"
        host = BaseUrl
//        host = BaseUrl
        port = 80
    }
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Napier.d(message = message)
            }
        }
    }
}