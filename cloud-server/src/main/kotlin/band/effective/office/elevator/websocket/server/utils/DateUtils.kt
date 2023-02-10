package band.effective.office.elevator.websocket.server.utils

import io.ktor.util.date.*

fun GMTDate.toVerifiableDate(): String {
    return "${this.dayOfMonth}.${this.month}.${this.year} ${this.hours}:${this.minutes}"
}