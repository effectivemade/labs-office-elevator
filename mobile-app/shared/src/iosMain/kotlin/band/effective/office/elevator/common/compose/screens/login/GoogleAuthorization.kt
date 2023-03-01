package band.effective.office.elevator.common.compose.screens.login

import band.effective.office.elevator.BuildKonfig
import io.github.aakira.napier.Napier

actual object GoogleAuthorization {

    private const val TAG = "GoogleAuthorization"

    private lateinit var signInCallback: () -> Unit
    private lateinit var signOutCallback: () -> Unit
    private lateinit var getLastSignedAccountCallback: () -> Unit

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
        signInCallback()
    }

    actual fun signOut() {
        this.signOutCallback()
    }

    actual suspend fun performWithFreshToken(
        action: (token: String) -> Unit,
        failure: (message: String) -> Unit
    ) {
        try {
            getLastSignedAccountCallback()
            token?.let { action(it) } ?: failure("Something went wrong. Please try again")
        } catch (e: Exception) {
            Napier.d(
                tag = TAG,
                message = "PerformWithFreshToken cause error",
                throwable = e
            )
            e.message?.let { failure(it) }
        }
    }

    fun setSignIn(callback: () -> Unit) {
        this.signInCallback = callback
    }

    fun signOut(callback: () -> Unit) {
        this.signOutCallback = callback
    }

    fun setLastSignedAccount(callback: () -> Unit) {
        this.getLastSignedAccountCallback = callback
    }

    fun getClientId(): String {
        return BuildKonfig.iosClient
    }
    fun getServerClientId(): String {
        return BuildKonfig.webClient
    }
}