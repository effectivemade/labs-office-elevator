package model

import java.lang.IllegalArgumentException
import java.util.GregorianCalendar

enum class Freq {
    DAILY, WEEKLY, MONTHLY, YEARLY
}

sealed class Ending {
    data class Count(val value: Int) : Ending()
    data class Until(val value: Long) : Ending()
    object Empty: Ending()
}

data class Recurrence(
    val interval: Int,
    val freq: Freq,
    val ending: Ending,
    val byDay: List<Int>,
    val byMonth: List<Int>,
    val byYearDay: List<Int>,
    val byHour: List<Int>
) {
    companion object {
        fun RecurrenceDTO.toRecurrence(): Recurrence = Recurrence(
            interval = (interval ?: 0).apply { if (this < 0) throw IllegalArgumentException() },
            freq = Freq.valueOf(freq),
            ending = when {
                count != null && until != null -> throw IllegalArgumentException()
                count != null -> Ending.Count(count)
                until != null -> Ending.Until(until)
                else -> Ending.Empty
            },
            byDay = byDay.onEach { day -> if (day !in 1..7) throw IllegalArgumentException() },
            byMonth = byMonth.onEach { month -> if (month !in 1..12) throw IllegalArgumentException() },
            byYearDay = byYearDay.onEach { yearDay -> if (yearDay !in 1..365) throw IllegalArgumentException() },
            byHour = byHour.onEach { hour -> if (hour !in 0..23) throw IllegalArgumentException() }
        )
    }

    fun toDto(): RecurrenceDTO = RecurrenceDTO(
        interval = if (interval != 0) interval else null,
        freq = freq.name,
        count = if (ending is Ending.Count) ending.value else null,
        until = if (ending is Ending.Until) ending.value else null,
        byDay = byDay,
        byMonth = byMonth,
        byYearDay = byYearDay,
        byHour = byHour
    )
}