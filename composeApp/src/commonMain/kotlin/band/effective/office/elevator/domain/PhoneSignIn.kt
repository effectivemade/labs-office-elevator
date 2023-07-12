package band.effective.office.elevator.domain

interface PhoneSignIn {
    fun signIn(callback: PhoneSignInResult)
}