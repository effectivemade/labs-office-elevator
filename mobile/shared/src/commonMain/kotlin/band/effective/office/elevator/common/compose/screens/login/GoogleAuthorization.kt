package band.effective.office.elevator.common.compose.screens.login

expect object GoogleAuthorization {
    var token: String?
    fun signIn(
        onSignInSuccess: () -> Unit,
        onSignInFailure: (e: Exception) -> Unit
    )

    fun signOut()
    suspend fun performWithFreshToken(
        action: (token: String) -> Unit,
        failure: (message: String) -> Unit
    )

    fun getAccount(): GoogleAccountUser
}