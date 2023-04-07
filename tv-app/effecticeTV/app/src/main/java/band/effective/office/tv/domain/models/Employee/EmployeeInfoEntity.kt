package band.effective.office.tv.domain.models.Employee

import band.effective.office.tv.utils.DateUtlils

class EmployeeInfoEntity(
    val firstName: String,
    val startDate: String,
    val nextBirthdayDate: String,
    val photoUrl: String,
)

fun processEmployeeInfo(data: List<EmployeeInfoEntity>): MutableList<EmployeeInfo> {
    val resultList = mutableListOf<EmployeeInfo>()
    data.map {
        if (DateUtlils.isCelebrationToday(it.nextBirthdayDate)) {
            resultList.add(
                Birthday(
                    it.firstName,
                    it.photoUrl,
                )
            )
        }
        if (DateUtlils.isCelebrationToday(it.startDate)) {
            resultList.add(
                Anniversary(
                    it.firstName,
                    it.photoUrl,
                    DateUtlils.getYearsWithTheCompany(it.startDate)
                )
            )
        }
        if (DateUtlils.isNewEmployeeToday(it.startDate)) {
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


