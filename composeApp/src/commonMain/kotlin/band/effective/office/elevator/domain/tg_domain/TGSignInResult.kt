package band.effective.office.elevator.domain.tg_domain

interface TGSignInResult {
    fun onSuccess(nick: String)
    fun onFailure(message: String)
}