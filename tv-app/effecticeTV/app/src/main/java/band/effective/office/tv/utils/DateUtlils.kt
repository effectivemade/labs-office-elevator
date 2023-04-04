package band.effective.office.tv.utils

import android.util.Log
import java.util.*

object DateUtlils {
    fun isCelebrationToday(date: String): Boolean {
        val dateInfo = date.split('-')
        val dayOfMonth = dateInfo[2].toInt()
        val monthNumber = dateInfo[1].toInt()
        val calendar = Calendar.getInstance()
        return (calendar.get(Calendar.DAY_OF_MONTH) == dayOfMonth && calendar.get(
            Calendar.MONTH
        ) + 1 == monthNumber)
    }

    fun isNewEmployeeToday(date: String): Boolean {
        val dateInfo = date.split('-')
        val dayOfMonth = dateInfo[2].toInt()
        val monthNumber = dateInfo[1].toInt()
        val year = dateInfo[0].toInt()
        val employeeStartWorkingDay = Calendar.getInstance()
        employeeStartWorkingDay.set(year, monthNumber - 1, dayOfMonth)
        val startDate = Calendar.getInstance()
        startDate.add(Calendar.DAY_OF_MONTH, -7)
        val endDate = Calendar.getInstance()
        endDate.add(Calendar.DAY_OF_MONTH, 7)
        val dateToCheck: Calendar = Calendar.getInstance()
        dateToCheck.set(year, monthNumber, dayOfMonth)
        return (employeeStartWorkingDay.after(startDate) && employeeStartWorkingDay.before(endDate))
    }

    fun getYearsWithTheCompany(date: String): Int {
        val dateInfo = date.split('-')
        return Calendar.getInstance()
            .get(Calendar.YEAR) - dateInfo[0].toInt()
    }
}