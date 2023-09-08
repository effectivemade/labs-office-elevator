package model

import com.google.api.client.util.DateTime
import java.lang.IllegalArgumentException
import java.lang.StringBuilder
import java.util.Date

enum class Freq {
    DAILY, WEEKLY, MONTHLY, YEARLY
}

sealed class Ending {
    data class Count(val value: Int) : Ending()
    data class Until(val value: String) : Ending()
    object Empty : Ending()
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
        fun RecurrenceDTO.toRecurrence(): Recurrence =
            Recurrence(interval = (interval ?: 0).apply { if (this < 0) throw IllegalArgumentException() },
                freq = Freq.valueOf(freq),
                ending = when {
                    count != null && until != null -> throw IllegalArgumentException()
                    count != null -> Ending.Count(count)
                    until != null -> Ending.Until(toDateRfc5545(until))//(until.toString())

                    else -> Ending.Empty
                },
                byDay = byDay.onEach { day -> if (day !in 1..7) throw IllegalArgumentException() },
                byMonth = byMonth.onEach { month -> if (month !in 1..12) throw IllegalArgumentException() },
                byYearDay = byYearDay.onEach { yearDay -> if (yearDay !in 1..365) throw IllegalArgumentException() },
                byHour = byHour.onEach { hour -> if (hour !in 0..23) throw IllegalArgumentException() })

        /**
         * Converts milliseconds date into exdate format from RFC5545
         *
         * @param millisDate - date in milliseconds ([Long])
         * @return [String] - date in DATE-TIME (RFC5545). Example: 20231015T200000Z
         * @author Kiselev Danil
         * */
        private fun toDateRfc5545(millisDate: Long): String {
            val dt = DateTime(millisDate)
            val sb = StringBuilder();
            dt.toStringRfc3339().split('-', ':', ',', ' ', '+', '.').dropLast(3).forEach { sb.append(it) }
            sb.append('Z')
            return sb.toString().trim('[', ']')
        }
    }

    fun toDto(): RecurrenceDTO = RecurrenceDTO(
        interval = if (interval != 0) interval else null,
        freq = freq.name,
        count = if (ending is Ending.Count) ending.value else null,
        until = if (ending is Ending.Until) parceUntil(ending.value) else null,
        byDay = byDay,
        byMonth = byMonth,
        byYearDay = byYearDay,
        byHour = byHour
    )

    private fun parceUntil(untilStr: String): Long {
        //2023 10 15 T 20 00 00 Z
        //0123 45 67 8 90 12 34 5
        val sb = StringBuilder()
        val str = untilStr.toCharArray()

        //get year
        for (i in 0..3) {
            sb.append(untilStr.toCharArray()[i])
        }
        val year: Int = sb.toString().toInt()

        // get month
        sb.clear()
        sb.append(str[4])
        sb.append(str[5])
        val month: Int = sb.toString().toInt()

        // get day
        sb.clear()
        sb.append(str[6])
        sb.append(str[7])
        val day: Int = sb.toString().toInt()

        // get hour
        sb.clear()
        sb.append(str[9])
        sb.append(str[10])
        val hour: Int = sb.toString().toInt()

        // get min
        sb.clear()
        sb.append(str[11])
        sb.append(str[12])
        val min: Int = sb.toString().toInt()

        // get sec
        sb.clear()
        sb.append(str[13])
        sb.append(str[14])
        val sec: Int = sb.toString().toInt()

        val dt = Date(year - 1900, month, day, hour, min, sec)
        return dt.time
    }
}