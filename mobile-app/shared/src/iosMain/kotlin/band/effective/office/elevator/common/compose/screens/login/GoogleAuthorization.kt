package band.effective.office.elevator.common.compose.screens.login

actual object GoogleAuthorization {

    private lateinit var signInCallback: () -> Unit
    private lateinit var signOutCallback: () -> Unit

    lateinit var onSignInSuccess: () -> Unit
    lateinit var onSignInFailure: (e: Exception) -> Unit

    actual var token: String? = null
        set(value) {
            field = if (value?.isNotBlank() == true) value else null
        }

    actual fun signIn(
        onSignInSuccess: () -> Unit,
        onSignInFailure: (e: Exception) -> Unit
    ) {
        this.onSignInSuccess = onSignInSuccess
        this.onSignInFailure = onSignInFailure
        try {
            signInCallback()
        } catch (e: Exception) {
            onSignInFailure(e)
        }
    }

    actual fun signOut() {
        this.signOutCallback()
    }

    fun setSignIn(callback: () -> Unit) {
        this.signInCallback = callback
    }

    fun signOut(callback: () -> Unit) {
        this.signOutCallback = callback
    }
}