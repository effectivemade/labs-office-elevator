package band.effective.office.elevator.common.compose.screens.login

import android.content.Intent
import android.util.Log
import band.effective.office.elevator.common.compose.Android
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


actual object GoogleAuthorization {

    private const val TAG: String = "GoogleAuthorization"
    private const val RC_SIGN_IN: Int = 1234

    // Configure sign-in to request the user's ID, email address, and basic
    // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("627570596451-iaj3hh94lbqognrkgk7vfmtj10c9v69b.apps.googleusercontent.com")
        .requestEmail()
        .build()

    private val isSigned: Boolean

    private lateinit var onSignInFailure: (e: Exception) -> Unit
    private lateinit var onSignInSuccess: () -> Unit

    actual var token: String? = null
        set(value) {
            field = if (value?.isNotBlank() == true) value else null
        }

    init {

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(Android.context.applicationContext)
        isSigned = account != null
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
        val client = GoogleSignIn.getClient(Android.context.applicationContext, gso)
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
        with(Android.activity) {
            val client = GoogleSignIn.getClient(this.applicationContext, gso)
            val signInIntent: Intent = client.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
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
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            onSignInFailure(e)
        } catch (e: Exception) {
            Log.e(TAG, "Failed handle sign in result", e)
            onSignInFailure(e)
        }
    }

    private fun printAccountInfo(account: GoogleSignInAccount) {
        Log.d(
            TAG, "\nSigned profile:\n displayName = ${account.displayName}\n" +
                    "email = ${account.email}\n" +
                    "photo = ${account.photoUrl}\n" +
                    "token = ${account.idToken}"
        )
    }
}