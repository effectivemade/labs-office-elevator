package office.effective.feature.auth.service

import office.effective.feature.auth.dto.UserDTO
import office.effective.model.UserModel

interface IUserService {
    fun getUsersByTag(): UserDTO;
    fun getUserById(): UserDTO;
    fun alterUser(): UserDTO;
    fun getUserByToken(tokenStr: String): UserDTO;
}