package office.effective.feature.auth.service

import office.effective.feature.auth.dto.UserDTO
import office.effective.model.UserModel

interface IUserService {
    fun getUsersByTag(tagStr: String): Set<UserDTO>?;
    fun getUserById(userIdStr: String): UserDTO;
    fun alterUser(user: UserDTO): UserDTO;
    fun getUserByToken(tokenStr: String): UserDTO;
}