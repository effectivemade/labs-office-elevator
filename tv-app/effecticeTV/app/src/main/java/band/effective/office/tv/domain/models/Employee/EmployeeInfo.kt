package band.effective.office.tv.domain.models.Employee

sealed class EmployeeInfo(val name: String, val photoUrl: String, val congratulations: String)

class Birthday(
    val employeeName: String,
    val employeePhotoUrl: String,
    val employeeCongratulations: String
) : EmployeeInfo(employeeName, employeePhotoUrl, employeeCongratulations) {}

class Anniversary(
    val employeeName: String,
    val employeePhotoUrl: String,
    val employeeCongratulations: String
) : EmployeeInfo(employeeName, employeePhotoUrl, employeeCongratulations)

class NewEmployee(
    val employeeName: String,
    val employeePhotoUrl: String,
    val employeeCongratulations: String
) : EmployeeInfo(employeeName, employeePhotoUrl, employeeCongratulations)
