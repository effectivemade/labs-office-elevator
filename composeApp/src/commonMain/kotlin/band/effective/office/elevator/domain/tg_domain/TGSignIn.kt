package band.effective.office.elevator.domain.tg_domain

interface TGSignIn {
    fun signIn(callback: TGSignInResult)
}