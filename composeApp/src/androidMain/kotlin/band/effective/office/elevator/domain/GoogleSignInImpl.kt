package band.effective.office.elevator.domain

import band.effective.office.elevator.AndroidApp
import band.effective.office.elevator.AppActivity
import band.effective.office.elevator.data.ApiResponse
import band.effective.office.elevator.domain.models.GoogleAccount
import band.effective.office.elevator.utils.LastOpenActivityProvider
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.google.android.gms.auth.api.signin.GoogleSignIn as GoogleAuthorization


class GoogleSignInImpl : GoogleSignIn {

    private val activity = LastOpenActivityProvider.lastOpenedActivity()!! as AppActivity

    override suspend fun retrieveAuthorizedUser(): ApiResponse<GoogleAccount, Error> {
        return suspendCoroutine { continuation ->
            val account: GoogleSignInAccount? =
                GoogleAuthorization.getLastSignedInAccount(AndroidApp.INSTANCE)
            continuation.resume(
                if (account == null) {
                    ApiResponse.Error.UnknownError
                } else {
                    ApiResponse.Success(
                        GoogleAccount(
                            email = account.email!!,
                            name = account.displayName!!,
                            photoUrl = account.photoUrl.toString(),
                            idToken = account.idToken
                        )
                    )
                }
            )
        }
    }


    override fun signIn(callback: SignInResultCallback) {
        activity.appActivityLifecycleObserver.signIn(callback)
    }

    override fun signOut() {
        activity.appActivityLifecycleObserver.signOut()
    }

}
