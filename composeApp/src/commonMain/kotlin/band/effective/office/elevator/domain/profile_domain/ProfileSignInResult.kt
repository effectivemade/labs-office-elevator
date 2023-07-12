package band.effective.office.elevator.domain.profile_domain

interface ProfileSignInResult {
    fun onSuccess(name: String, function: String)
    fun onFailure(message: String)
}