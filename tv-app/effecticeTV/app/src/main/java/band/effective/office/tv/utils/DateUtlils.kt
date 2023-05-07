package band.effective.office.tv.utils

import java.util.*

object DateUtlils {
    fun getYearsFromStartDate(date: String): Int {
        val dateInfo = date.split('-')
        return Calendar.getInstance()
            .get(Calendar.YEAR) - dateInfo[0].toInt()
    }
}