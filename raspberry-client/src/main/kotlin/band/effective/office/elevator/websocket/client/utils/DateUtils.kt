package band.effective.office.elevator.websocket.client.utils

import io.ktor.http.*
import io.ktor.util.date.*
import kotlin.math.abs

object DateUtils {
    fun isCorrectTime(time: GMTDate?): Boolean {
        return if (time != null) {
            val today = GMTDate()
            val milliseconds: Long = today.timestamp - time.timestamp
            Logger.debug {
                "\ntoday: $today \n providedTime: $time \n milliseconds: $milliseconds"
            }
            (abs(milliseconds) / 1000) < 60
        } else false
    }
}

fun String.toGMTDate(): GMTDate {
    return this.fromHttpToGmtDate()
}