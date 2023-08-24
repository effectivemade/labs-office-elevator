package office.effective.features.user.converters

import office.effective.common.exception.MissingIdException
import office.effective.features.user.repository.UserEntity
import office.effective.model.IntegrationModel
import office.effective.model.UserModel

class UserModelEntityConverter {

    /**
     * Converts user model to entity
     *
     * @throws MissingIdException if the user id is null
     *
     * @author Danil Kiselev, Daniil Zavyalov
     */
    fun modelToEntity(userModel: UserModel): UserEntity {
        return UserEntity {
            id = userModel.id ?: throw MissingIdException("User with name ${userModel.fullName} doesn't have an id")
            fullName = userModel.fullName
            tag = userModel.tag!!
            active = userModel.active
            role = userModel.role
            avatarURL = userModel.avatarURL
            email = userModel.email
        }
    }

    /**
     * Converts user entity to model
     *
     * @author Danil Kiselev, Daniil Zavyalov
     */
    fun entityToModel(userEntity: UserEntity, integrations: Set<IntegrationModel>?): UserModel {

        return UserModel(
            fullName = userEntity.fullName,
            id = userEntity.id,
            tag = userEntity.tag,
            active = userEntity.active,
            role = userEntity.role,
            avatarURL = userEntity.avatarURL,
            integrations = integrations,
            email = userEntity.email
        )

    }
}