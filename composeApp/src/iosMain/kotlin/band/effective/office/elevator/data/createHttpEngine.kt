package band.effective.office.elevator.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

actual fun createHttpEngine(enableLogging: Boolean): HttpClient = HttpClient(Darwin)