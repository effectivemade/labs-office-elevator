package band.effective.office.elevator.domain

import band.effective.office.elevator.data.ApiResponse
import band.effective.office.elevator.domain.models.GoogleAccount

interface GoogleSignIn {
    suspend fun retrieveAuthorizedUser(): ApiResponse<GoogleAccount, Error>

    /**
     * Sign in
     *
     */
    fun signIn(callback: SignInResultCallback)

    /**
     * Sign out
     */
    fun signOut()
}
