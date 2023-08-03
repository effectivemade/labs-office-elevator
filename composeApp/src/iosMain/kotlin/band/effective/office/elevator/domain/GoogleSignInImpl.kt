package band.effective.office.elevator.domain

import band.effective.office.elevator.MainRes
import band.effective.office.elevator.data.ApiResponse
import band.effective.office.elevator.domain.models.GoogleAccount
import band.effective.office.elevator.ui.uiViewController
import cocoapods.GoogleSignIn.GIDConfiguration
import cocoapods.GoogleSignIn.GIDSignIn
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import platform.UIKit.UIScreen
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.math.round

class GoogleSignInImpl(gidClientId: String, serverClientId: String) : GoogleSignIn {

    private val scope = CoroutineScope(Dispatchers.Main)

    init {
        GIDSignIn.sharedInstance.setConfiguration(
            configuration = GIDConfiguration(
                clientID = gidClientId, serverClientID = serverClientId
            )
        )
    }

    override suspend fun retrieveAuthorizedUser(): ApiResponse<GoogleAccount, Error> {
        return suspendCoroutine { continuation ->
            GIDSignIn.sharedInstance.restorePreviousSignInWithCompletion { result, _ ->
                scope.launch {
                    result?.profile?.let { profileData ->
                        val dimension = round(100 * UIScreen.mainScreen.scale)
                        continuation.resume(
                            ApiResponse.Success(
                                GoogleAccount(
                                    email = profileData.email,
                                    name = profileData.name,
                                    photoUrl = profileData.imageURLWithDimension(dimension = dimension.toULong())?.absoluteString,
                                    idToken = result.idToken?.tokenString
                                )
                            )
                        )
                    } ?: continuation.resume(ApiResponse.Error.UnknownError)
                }
            }
        }
    }

    override fun signIn(callback: SignInResultCallback) {
        GIDSignIn.sharedInstance.signInWithPresentingViewController(uiViewController) { result, error ->
            if (result == null) callback.onFailure(MainRes.strings.something_went_wrong.desc().localized())
            else callback.onSuccess()
        }
    }

    override fun signOut() {
        GIDSignIn.sharedInstance.signOut()
    }
}
