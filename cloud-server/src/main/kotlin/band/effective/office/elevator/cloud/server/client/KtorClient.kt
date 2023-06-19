package band.effective.office.elevator.cloud.server.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging


val ktorClient = HttpClient(CIO) {
    defaultRequest {
//        host = "92.124.138.130"
        host = "0.0.0.0"
        port = 2105
    }
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.ALL
    }
}