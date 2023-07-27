package band.effective.office.elevator.domain.models

data class EmployeeInfo(
    val name: String,
    val post: String,
    val state: Boolean,
    val logoUrl: String,
    val phoneNum: String,
    val eMail: String,
    val telegramProfile: String
)
