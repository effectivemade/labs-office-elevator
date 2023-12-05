package band.effective.office.elevator.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun localDateTimeToUnix(date: LocalDateTime?) =
    date?.toInstant(timeZone = TimeZone.currentSystemDefault())?.toEpochMilliseconds()

fun unixToLocalDateTime(date: Long) =
    Instant.fromEpochMilliseconds(date).toLocalDateTime(timeZone = TimeZone.currentSystemDefault())