package band.effective.office.elevator.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android

actual fun createHttpEngine(enableLogging: Boolean): HttpClient = HttpClient(Android)
