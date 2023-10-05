package band.effective.office.elevator.domain.useCase

import band.effective.office.elevator.data.ApiResponse
import band.effective.office.elevator.domain.GoogleSignIn
import band.effective.office.elevator.domain.SignInResultCallback
import band.effective.office.elevator.domain.models.GoogleAccount
import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.domain.repository.AuthorizationRepository
import band.effective.office.network.model.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthorizationUseCase(
    private val authorizationRepository: AuthorizationRepository,
    private val googleSignIn: GoogleSignIn
) {
    suspend fun authorize(
        scope: CoroutineScope,
        successCallBack: (User) -> Unit,
        failureCallBack: (String) -> Unit,
    ) = googleSignIn.signIn(object : SignInResultCallback {
            override fun onSuccess(googleAccount: GoogleAccount) {
                scope.launch  {
                    when (val result = googleSignIn.retrieveAuthorizedUser()) {
                        is ApiResponse.Error.HttpError -> {}
                        ApiResponse.Error.NetworkError -> {}
                        ApiResponse.Error.SerializationError -> {}
                        ApiResponse.Error.UnknownError -> {}
                        is ApiResponse.Success -> {
                            withContext(Dispatchers.IO) {
                                result.body.idToken?.let { token ->
                                    authorizationRepository
                                        .authorizeUser(idToken = token, email = result.body.email)
                                        .collect { response ->
                                            when (response) {
                                                is Either.Success -> {
                                                    withContext(Dispatchers.Main) {
                                                        println("autorize success")
                                                        successCallBack(response.data)
                                                    }
                                                }
                                                is Either.Error -> {
                                                    println("autorize error ${response.error.description}")
                                                    // TODO show error and data
                                                }
                                            }
                                        }
                                }
                            }
                        }
                    }
                }
            }
            override fun onFailure(message: String) {
                failureCallBack(message)
            }
        })

    fun logout() {
        googleSignIn.signOut()
        authorizationRepository.logout()
    }
}