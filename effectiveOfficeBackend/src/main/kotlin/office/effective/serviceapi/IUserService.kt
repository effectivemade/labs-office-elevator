package office.effective.serviceapi

import office.effective.common.exception.InstanceNotFoundException
import office.effective.features.user.repository.UserEntity
import office.effective.features.user.repository.users
import office.effective.model.UserModel
import org.ktorm.dsl.eq
import org.ktorm.entity.find

/**
 * Interface that provides methods to manipulate [UserModel] objects
 * */
interface IUserService {

    /**
     * Return all users with given tag
     *
     * @param tagStr name of the tag
     * @return Set<UserModel>
     * @author Kiselev Danil
     * */
    fun getUsersByTag(tagStr: String): Set<UserModel>;

    /**
     * Return all users in database
     *
     * @return Set<UserModel>
     * @author Daniil Zavyalov
     * */
    fun getAll(): Set<UserModel>

    /**
     * Return user by id, or returns null, if user not found
     *
     * @param userIdStr string value of user id
     * @return UserModel or null
     * @author Kiselev Danil
     * */
    fun getUserById(userIdStr: String): UserModel?;

    /**
     * Change existed user in database
     *
     * @param user User's model
     * @return UserModel
     * @author Kiselev Danil
     * */
    fun updateUser(user: UserModel): UserModel;

    /**
     * Return user with specified email
     *
     * @param emailStr user email
     * @return UserModel
     * @author Kiselev Danil
     * */
    fun getUserByEmail(emailStr: String): UserModel?;

    /**
     * Checks if a user with the specified email exists in the database.
     *
     * @param email The email of the user to check.
     * @return true if a user with the specified email exists, false otherwise.
     * @author Kiselev Danil
     * */
    fun existsByEmail(email: String): Boolean

    /**
     *Updates the avatar URL for a user with the specified email.
     *
     *  @param email The email of the user to update.
     *  @param newAvatar The new avatar URL to set.
     *  @throws InstanceNotFoundException if user with the specified email is not found.
     * @author Kiselev Danil
     * */
    fun updateAvatar(email: String, newAvatar: String?)
}