package office.effective.features.auth.service

import office.effective.features.auth.dto.UserDTO

interface IUserService {
    fun getUsersByTag(tagStr: String, token: String): Set<UserDTO>?;
    fun getUserById(userIdStr: String, token: String): UserDTO;
    fun updateUser(user: UserDTO, token: String): UserDTO;
    fun getUserByToken(tokenStr: String): UserDTO;
}