package band.effective.office.tv.domain.models.Employee

sealed class EmployeeInfo(val name: String, val photoUrl: String, val eventType: EventType)

class Birthday(
    private val employeeName: String,
    private val employeePhotoUrl: String,
) : EmployeeInfo(employeeName, employeePhotoUrl, EventType.Birthday)

class Anniversary(
    private val employeeName: String,
    private val employeePhotoUrl: String,
    val employeeCongratulations: Int
) : EmployeeInfo(employeeName, employeePhotoUrl, EventType.Anniversary)

class NewEmployee(
    private val employeeName: String,
    private val employeePhotoUrl: String,
) : EmployeeInfo(employeeName, employeePhotoUrl, EventType.NewEmployee)
