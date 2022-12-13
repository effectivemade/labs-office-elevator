package band.effective.office.elevator.websocket.client.utils

import java.time.LocalDateTime
import java.time.ZoneOffset

object DateUtils {
    fun isCorrectTime(time: LocalDateTime?): Boolean {
        return if (time != null) {
            val today = LocalDateTime.now()
            val milliseconds: Long =
                today.toInstant(ZoneOffset.UTC).toEpochMilli() - time.toInstant(ZoneOffset.UTC)
                    .toEpochMilli()
            return (milliseconds / 1000) < 60
        } else false
    }
}

fun String.parseStringToISO8061Date(): LocalDateTime? {
    return LocalDateTime.parse(this)
}