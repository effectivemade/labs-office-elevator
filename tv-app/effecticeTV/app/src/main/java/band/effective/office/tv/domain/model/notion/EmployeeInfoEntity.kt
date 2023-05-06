package band.effective.office.tv.domain.model.notion

import band.effective.office.tv.utils.DateUtlils
import java.util.*

class EmployeeInfoEntity(
    val firstName: String,
    val startDate: String,
    val nextBirthdayDate: String,
    val photoUrl: String,
)

fun List<EmployeeInfoEntity>.processEmployeeInfo(): MutableList<EmployeeInfo> {
    val resultList = mutableListOf<EmployeeInfo>()
    this.map {employee ->
        if (employee.nextBirthdayDate.isNotBlank() && isCelebrationToday(employee.nextBirthdayDate)) {
            resultList.add(
                Birthday(
                    employee.firstName,
                    employee.photoUrl,
                )
            )
        }
        if (employee.startDate.isNotBlank() && isCelebrationToday(employee.startDate)) {
            resultList.add(
                Anniversary(
                    employee.firstName,
                    employee.photoUrl,
                    DateUtlils.getYearsFromStartDate(employee.startDate)
                )
            )
        }
        if (employee.startDate.isNotBlank() && isNewEmployeeToday(employee.startDate)) {
            resultList.add(
                NewEmployee(
                    employee.firstName,
                    employee.photoUrl,
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


