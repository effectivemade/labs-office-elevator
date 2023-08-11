package office.effective.features.user.service

import office.effective.features.user.repository.UserRepository
import office.effective.model.UserModel
import java.util.*

class UserService(private val repository: UserRepository) : IUserService {

    override fun getUsersByTag(tagStr: String): Set<UserModel> {
        return repository.findByTag(repository.findTagByName(tagStr).id)
    }

    override fun getUserById(userIdStr: String): UserModel {
        return repository.findById(UUID.fromString(userIdStr))
    }

    /**
     * Updates a given user. Use the returned model for further operations
     *
     * @author Danil Kiselev
     */
    override fun updateUser(user: UserModel): UserModel {
        return repository.updateUser(user)
    }

    override fun getUserByEmail(emailStr: String): UserModel {
        return repository.findByEmail(emailStr)
    }
}