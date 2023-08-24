package office.effective.features.user.service

import office.effective.model.UserModel

interface IUserService {
    fun getUsersByTag(tagStr: String): Set<UserModel>;
    fun getAll(): Set<UserModel>
    fun getUserById(userIdStr: String): UserModel?;
    fun updateUser(user: UserModel): UserModel;
    fun getUserByEmail(emailStr: String): UserModel?;
}