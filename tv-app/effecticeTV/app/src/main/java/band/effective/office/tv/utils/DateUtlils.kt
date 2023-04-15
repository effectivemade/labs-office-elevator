package band.effective.office.tv.utils

import android.util.Log
import java.util.*

object DateUtlils {
    fun getYearsWithTheCompany(date: String): Int {
        val dateInfo = date.split('-')
        return Calendar.getInstance()
            .get(Calendar.YEAR) - dateInfo[0].toInt()
    }
}