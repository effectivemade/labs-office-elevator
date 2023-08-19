package band.effective.office.elevator.data.repository

import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.domain.repository.AuthorizationRepository
import band.effective.office.network.api.Api
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import kotlinx.coroutines.flow.Flow

class AuthorizationRepositoryImpl(
    api: Api
):  AuthorizationRepository{
    override suspend fun authorizeUser(idToken: String): Flow<Either<ErrorResponse, User>> {

    }
}