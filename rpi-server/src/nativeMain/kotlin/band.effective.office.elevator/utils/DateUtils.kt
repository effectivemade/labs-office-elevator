package band.effective.office.elevator.utils

import io.ktor.http.*
import io.ktor.util.date.*
import kotlin.math.abs

object DateUtils {
    fun isCorrectTime(time: GMTDate?): Boolean {
        return if (time != null) {
            val today = GMTDate()
            val milliseconds: Long = today.timestamp - time.timestamp
            (abs(milliseconds) / 1000) < 60
        } else false
    }

    fun now(): String {
        return GMTDate().toHttpDate()
    }
}

fun String.toGMTDate() = fromHttpToGmtDate()

fun GMTDate.toVerifiableDate(): String {
    return "${this.dayOfMonth}.${this.month}.${this.year} ${this.hours}:${this.minutes}"
}