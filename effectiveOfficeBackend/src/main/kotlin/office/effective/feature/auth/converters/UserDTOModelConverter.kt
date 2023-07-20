package office.effective.feature.auth.converters

import office.effective.feature.auth.dto.UserDTO
import office.effective.feature.auth.repository.UsersTagEntity
import office.effective.feature.auth.repository.UsersTags
import office.effective.model.UserModel
import office.effective.model.UserTagModel
import java.util.UUID

class UserDTOModelConverter {

    fun dTOToModel(userDTO: UserDTO): UserModel {
        var res = UserModel(userDTO.fullName)
        res.id = UUID.fromString(userDTO.id)
        res.tag = UsersTagEntity()
        res.active = userDTO.active
        res.role = userDTO.role
        res.avatarURL = userDTO.avatarUrl
        return res
    }

    fun modelToDTO(userModel: UserModel): UserDTO {
        return UserDTO(
            id = userModel.id.toString(),
            fullName = userModel.fullName,
            active = userModel.active,
            role = userModel.role ?: "",
            avatarUrl = userModel.avatarURL ?: ""
        )
    }


}