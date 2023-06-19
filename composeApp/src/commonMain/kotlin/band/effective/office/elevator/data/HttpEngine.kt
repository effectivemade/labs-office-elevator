package band.effective.office.elevator.data

import io.ktor.client.HttpClient

expect fun createHttpEngine(enableLogging: Boolean): HttpClient