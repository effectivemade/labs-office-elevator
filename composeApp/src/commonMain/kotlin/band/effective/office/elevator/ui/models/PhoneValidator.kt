package band.effective.office.elevator.ui.models

abstract class PhoneValidator() {
    companion object {
        fun validatePhoneNumber(phoneNumber: String) = phoneNumber.length == 10
    }
}