package office.effective.feature.auth.converters

import office.effective.feature.auth.repository.UserEntity
import office.effective.model.IntegrationModel
import office.effective.model.UserModel

class UserModelEntityConverter {
    fun modelToEntity(userModel: UserModel): UserEntity {
        var res = UserEntity()
        res.id = userModel.id!!
        res.tag = userModel.tag
        res.active = userModel.active
        res.role = userModel.role
        res.avatarURL = userModel.avatarURL
        return res
    }

    fun EntityToModel(userEntity: UserEntity, integrations: Set<IntegrationModel>?): UserModel {

        return UserModel(
            userEntity.fullName,
            id = userEntity.id,
            tag = userEntity.tag,
            active = userEntity.active,
            role = userEntity.role,
            avatarURL = userEntity.avatarURL,
            integrations = integrations
        )

    }
}