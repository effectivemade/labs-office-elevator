package band.effective.office.elevator.domain.entity

import band.effective.office.elevator.domain.models.UserData
import band.effective.office.elevator.domain.useCase.GetUserUseCase
import band.effective.office.elevator.domain.useCase.PushUserDataUseCase

class AuthorizationEntity(
    private val getUserUseCase: GetUserUseCase,
    private val pushUserDataUseCase: PushUserDataUseCase
) {
    suspend fun get(idToken: String): UserData {
        val execute: UserData = getUserUseCase.execute(idToken)
        return execute
    }

    suspend fun push(userData: UserData): Boolean {
        val execute: Boolean = pushUserDataUseCase.execute(userData)
        return execute
    }
}