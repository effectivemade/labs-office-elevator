package band.effective.office.elevator.domain.entity

import band.effective.office.elevator.domain.models.UserData
import band.effective.office.elevator.domain.usecase.GetUserUseCase
import band.effective.office.elevator.domain.usecase.PushUserDataUseCase

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