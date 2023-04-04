package band.effective.office.tv.domain.models.Employee

sealed class EmployeeInfo(val name: String, val photoUrl: String, val eventType: EventType)

class Birthday(
    val employeeName: String,
    val employeePhotoUrl: String,
) : EmployeeInfo(employeeName, employeePhotoUrl, EventType.Birthday)

class Anniversary(
    val employeeName: String,
    val employeePhotoUrl: String,
    val employeeCongratulations: Int
) : EmployeeInfo(employeeName, employeePhotoUrl, EventType.Anniversary)

class NewEmployee(
    val employeeName: String,
    val employeePhotoUrl: String,
) : EmployeeInfo(employeeName, employeePhotoUrl, EventType.NewEmployee)
