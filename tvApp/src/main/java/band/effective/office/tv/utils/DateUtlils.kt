package band.effective.office.tv.utils

import java.util.Calendar

object DateUtlils {
    fun getYearsFromStartDate(date: String): Int {
        val dateInfo = date.split('-')
        return Calendar.getInstance()
            .get(Calendar.YEAR) - dateInfo[0].toInt()
    }
    fun getMonthsFromStartDate(date: String): Int {
        val dateInfo = date.split('-')
        return Calendar.getInstance()
            .get(Calendar.MONTH) + 1 - dateInfo[1].toInt()
    }

}