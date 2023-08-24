package band.effective.office.elevator.domain.entity

import band.effective.office.elevator.domain.models.ErrorWithData
import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.domain.repository.AuthorizationRepository
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import kotlinx.coroutines.flow.Flow

class AuthorizationUseCase(
    private val authorizationRepository: AuthorizationRepository,
) {
    suspend fun authorize(idToken: String, email: String): Flow<Either<ErrorResponse, User>>
        = authorizationRepository.authorizeUser(idToken = idToken, email = email)
}