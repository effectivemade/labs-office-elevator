package band.effective.office.elevator.common.compose.screens.login

import android.content.Intent
import band.effective.office.elevator.BuildKonfig
import band.effective.office.elevator.common.compose.Android
import band.effective.office.elevator.common.compose.expects.showToast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


actual object GoogleAuthorization {

    private const val TAG: String = "GoogleAuthorization"
    private const val RC_SIGN_IN: Int = 1234

    // Configure sign-in to request the user's ID, email address, and basic
    // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(BuildKonfig.webClient)
        .requestEmail()
        .build()

    private lateinit var onSignInFailure: (e: Exception) -> Unit
    private lateinit var onSignInSuccess: () -> Unit

    actual var token: String? = null
        set(value) {
            field = if (value?.isNotBlank() == true) value else null
        }

    init {

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account =
            GoogleSignIn.getLastSignedInAccount(Android.applicationContext.applicationContext)
        if (account != null) {
            printAccountInfo(account)
            token = account.idToken
        }
    }

    actual fun signIn(
        onSignInSuccess: () -> Unit,
        onSignInFailure: (e: Exception) -> Unit
    ) {
        this.onSignInFailure = onSignInFailure
        this.onSignInSuccess = onSignInSuccess
        startAuthorizationIntent()
    }

    actual fun signOut() {
        val client = GoogleSignIn.getClient(Android.applicationContext.applicationContext, gso)
        client.signOut()
    }

    fun activityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun startAuthorizationIntent() {
        val client = GoogleSignIn.getClient(Android.applicationContext, gso)
        val signInIntent: Intent = client.signInIntent

        CoroutineScope(Dispatchers.Main).launch {
            Android.activity.first()?.startActivityForResult(signInIntent, RC_SIGN_IN)
                ?: showToast("Something went wrong. Please try again")
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            printAccountInfo(account)
            token = account.idToken

            onSignInSuccess()

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Napier.e(tag = TAG, message = "signInResult:failed code=" + e.statusCode, throwable = e)
            onSignInFailure(e)
        } catch (e: Exception) {
            Napier.e(tag = TAG, throwable = e, message = "Failed handle sign in result")
            onSignInFailure(e)
        }
    }

    private fun printAccountInfo(account: GoogleSignInAccount) {
        Napier.d(
            TAG, null, "\nSigned profile:\n displayName = ${account.displayName}\n" +
                    "email = ${account.email}\n" +
                    "photo = ${account.photoUrl}\n" +
                    "token = ${account.idToken}"
        )
    }

    actual suspend fun performWithFreshToken(
        action: (token: String) -> Unit,
        failure: (message: String) -> Unit
    ) {
        val client = GoogleSignIn.getClient(Android.applicationContext, gso)
        val task = client.silentSignIn()
        if (task.isSuccessful) {
            val signInAccount = task.result
            signInAccount.idToken?.let { action(it) }
                ?: failure("Something went wrong. Please try again")
        } else {
            task.addOnCompleteListener { completedTask ->
                try {
                    val signInAccount = completedTask.getResult(
                        ApiException::class.java
                    )
                    signInAccount.idToken?.let { action(it) }
                        ?: failure("Something went wrong. Please try again")
                } catch (apiException: ApiException) {
                    Napier.e(message = "ApiException", tag = TAG, throwable = apiException)
                    apiException.message?.let { failure("Something went wrong. Please try again") }
                }
            }
        }
    }

    actual fun getAccount(): GoogleAccountUser {
        val account =
            GoogleSignIn.getLastSignedInAccount(Android.applicationContext.applicationContext)
        Napier.d { "photoUrl: ${account?.photoUrl}" }
        return GoogleAccountUser(
            imageUrl = account?.photoUrl.toString(),
            username = account?.displayName,
            email = account?.email
        )
    }
}