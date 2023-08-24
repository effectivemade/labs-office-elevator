package band.effective.office.elevator.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun localDateTimeToUnix(date: LocalDateTime?) =
    date?.toInstant(timeZone = TimeZone.currentSystemDefault())?.epochSeconds

fun unixToLocalDateTime(date: Long) =
    Instant.fromEpochSeconds(date).toLocalDateTime(timeZone = TimeZone.currentSystemDefault())