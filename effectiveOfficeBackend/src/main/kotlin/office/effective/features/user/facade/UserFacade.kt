package office.effective.features.user.facade

import office.effective.common.utils.DatabaseTransactionManager
import office.effective.features.user.dto.UserDTO
import office.effective.features.user.service.IUserService

class UserFacade(private val service: IUserService, private val transactionManager: DatabaseTransactionManager) {
    fun getUsersByTag(tagStr: String, token: String): Set<UserDTO>? {
        return transactionManager.useTransaction({ service.getUsersByTag(tagStr, token) })
    }

    fun getUserById(userIdStr: String, token: String): UserDTO {
        return transactionManager.useTransaction({ service.getUserById(userIdStr, token) })
    }

    fun updateUser(user: UserDTO, token: String): UserDTO {
        return transactionManager.useTransaction({ service.updateUser(user, token) })
    }

    fun getUserByToken(tokenStr: String): UserDTO {
        return transactionManager.useTransaction({ service.getUserByToken(tokenStr) })
    }
}