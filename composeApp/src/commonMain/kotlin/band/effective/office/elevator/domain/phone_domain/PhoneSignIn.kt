package band.effective.office.elevator.domain.phone_domain

interface PhoneSignIn {
    fun signIn(callback: PhoneSignInResult)
}