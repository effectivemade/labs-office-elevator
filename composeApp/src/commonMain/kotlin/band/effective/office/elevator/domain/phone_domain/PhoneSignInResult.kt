package band.effective.office.elevator.domain.phone_domain

interface PhoneSignInResult {
    fun onSuccess(phone: String)
    fun onFailure(message: String)
}