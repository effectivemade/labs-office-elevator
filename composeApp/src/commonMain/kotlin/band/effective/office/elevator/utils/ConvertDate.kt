package band.effective.office.elevator.utils

import androidx.compose.ui.text.intl.Locale
import kotlinx.datetime.Month

fun NumToMonth(month: Int): String {
    return when(month) {
        1 -> "Янв."
        2 -> "Фев."
        3 -> "Мар."
        4 -> "Апр."
        5 -> "Май"
        6 -> "Июнь"
        7 -> "Июль"
        8 -> "Авг."
        9 -> "Сен."
        10 -> "Окт."
        11 -> "Ноя."
        12 -> "Дек."
        else -> "Unknown"
    }
}

object MonthLocalizations {
    private val monthNames: Map<Month, Map<Locale, String>> = mapOf(
        Month.JANUARY to mapOf(Locale("ru") to "Январь", Locale("en") to "January"),
        Month.FEBRUARY to mapOf(Locale("ru") to "Февраль", Locale("en") to "February"),
        Month.MARCH to mapOf(Locale("ru") to "Март", Locale("en") to "March"),
        Month.APRIL to mapOf(Locale("ru") to "Апрель", Locale("en") to "April"),
        Month.MAY to mapOf(Locale("ru") to "Май", Locale("en") to "May"),
        Month.JUNE to mapOf(Locale("ru") to "Июнь", Locale("en") to "June"),
        Month.JULY to mapOf(Locale("ru") to "Июль", Locale("en") to "July"),
        Month.AUGUST to mapOf(Locale("ru") to "Август", Locale("en") to "August"),
        Month.SEPTEMBER to mapOf(Locale("ru") to "Сентябрь", Locale("en") to "September"),
        Month.OCTOBER to mapOf(Locale("ru") to "Октябрь", Locale("en") to "October"),
        Month.NOVEMBER to mapOf(Locale("ru") to "Ноябрь", Locale("en") to "November"),
        Month.DECEMBER to mapOf(Locale("ru") to "Декабрь", Locale("en") to "December")
    )

    fun getMonthName(month: Month, locale: Locale): String {
        return monthNames[month]?.get(locale) ?: ""
    }
}