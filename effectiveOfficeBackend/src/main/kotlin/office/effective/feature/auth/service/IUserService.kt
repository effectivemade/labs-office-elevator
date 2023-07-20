package office.effective.feature.auth.service

import office.effective.feature.auth.dto.UserDTO
import office.effective.model.UserModel

interface IUserService {
    fun getUsersByTag(tagStr: String, token: String): Set<UserDTO>?;
    fun getUserById(userIdStr: String, token: String): UserDTO;
    fun updateUser(user: UserDTO, token: String): UserDTO;
    fun getUserByToken(tokenStr: String): UserDTO;
}