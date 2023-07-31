package office.effective.features.user.service

import office.effective.features.user.dto.UserDTO

interface IUserService {
    fun getUsersByTag(tagStr: String, token: String): Set<UserDTO>?;
    fun getUserById(userIdStr: String, token: String): UserDTO;
    fun updateUser(user: UserDTO, token: String): UserDTO;
    fun getUserByToken(tokenStr: String): UserDTO;
}