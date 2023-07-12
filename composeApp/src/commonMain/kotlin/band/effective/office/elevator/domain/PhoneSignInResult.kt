package band.effective.office.elevator.domain

interface PhoneSignInResult {
    fun onSuccess(phone: String)
    fun onFailure(message: String)
}