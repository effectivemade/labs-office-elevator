package band.effective.office.tv.domain.model.notion

import band.effective.office.tv.screen.eventStory.models.*
import band.effective.office.tv.utils.DateUtlils
import java.util.Calendar

class EmployeeInfoEntity(
    val firstName: String,
    val startDate: String,
    val nextBirthdayDate: String,
    val photoUrl: String,
)

fun List<EmployeeInfoEntity>.processEmployeeInfo(): List<EmployeeInfoUI> =
    map { employee ->
        listOfNotNull(
            if (employee.nextBirthdayDate.isNotBlank() && isYearCelebrationToday(employee.nextBirthdayDate)) {
                BirthdayUI(
                    employee.firstName,
                    employee.photoUrl,
                )
            } else null,
            if (employee.startDate.isNotBlank() && isFirstOrThirdMonthCelebrationToday(employee.startDate)) {
                MonthAnniversaryUI(
                    employee.firstName,
                    employee.photoUrl,
                    DateUtlils.getYearsFromStartDate(employee.startDate),
                    DateUtlils.getMonthsFromStartDate(employee.startDate),
                ).run { if (yearsInCompany == 0) this else null }
            } else null,
            if (employee.startDate.isNotBlank() && isYearCelebrationToday(employee.startDate)) {
                AnnualAnniversaryUI(
                    employee.firstName,
                    employee.photoUrl,
                    DateUtlils.getYearsFromStartDate(employee.startDate)
                ).run { if (yearsInCompany == 0) null else this }
            } else null,
            if (employee.startDate.isNotBlank() && isNewEmployeeToday(employee.startDate)) {
                NewEmployeeUI(
                    employee.firstName,
                    employee.photoUrl,
                )
            } else null
        )
    }.flatten()

fun isYearCelebrationToday(date: String): Boolean {
    val dateInfo = date.split('-')
    val dayOfMonth = dateInfo[2].toInt()
    val monthNumber = dateInfo[1].toInt()
    val calendar = Calendar.getInstance()
    return (calendar.get(Calendar.DAY_OF_MONTH) == dayOfMonth
            && calendar.get(Calendar.MONTH) + 1 == monthNumber)
}

fun isFirstOrThirdMonthCelebrationToday(date: String): Boolean {
    val dateInfo = date.split('-')
    val dayOfMonth = dateInfo[2].toInt()
    val monthNumber = dateInfo[1].toInt()
    val year = dateInfo[0].toInt()
    val calendar = Calendar.getInstance()
    return (calendar.get(Calendar.DAY_OF_MONTH) == dayOfMonth
            && (calendar.get(Calendar.MONTH)  == monthNumber ||
            calendar.get(Calendar.MONTH) - 2 == monthNumber) && year == calendar.get(Calendar.YEAR))
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