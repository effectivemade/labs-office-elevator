package office.effective.feature.auth.converters

import office.effective.feature.auth.repository.UserEntity
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

    fun EntityToModel(userEntity: UserEntity): UserModel {
        var res = UserModel(userEntity.fullName)
        res.id = userEntity.id
        res.tag = userEntity.tag
        res.active = userEntity.active
        res.role = userEntity.role
        res.avatarURL = userEntity.avatarURL
        return res

    }
}