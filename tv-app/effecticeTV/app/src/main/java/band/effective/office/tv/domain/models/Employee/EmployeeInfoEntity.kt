package band.effective.office.tv.domain.models.Employee

import band.effective.office.tv.utils.DateUtlils
import java.util.*

class EmployeeInfoEntity(
    val firstName: String,
    val startDate: String,
    val nextBirthdayDate: String,
    val photoUrl: String,
)

fun processEmployeeInfo(data: List<EmployeeInfoEntity>): MutableList<EmployeeInfo> {
    val resultList = mutableListOf<EmployeeInfo>()
    data.map {
        if (isCelebrationToday(it.nextBirthdayDate)) {
            resultList.add(
                Birthday(
                    it.firstName,
                    it.photoUrl,
                )
            )
        }
        if (isCelebrationToday(it.startDate)) {
            resultList.add(
                Anniversary(
                    it.firstName,
                    it.photoUrl,
                    DateUtlils.getYearsWithTheCompany(it.startDate)
                )
            )
        }
        if (isNewEmployeeToday(it.startDate)) {
            resultList.add(
                NewEmployee(
                    it.firstName,
                    it.photoUrl,
                )
            )
        }
    }
    return resultList
}

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


