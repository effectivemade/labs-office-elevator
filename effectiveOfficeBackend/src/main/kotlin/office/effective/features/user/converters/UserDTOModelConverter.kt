package office.effective.features.user.converters

import office.effective.features.user.dto.UserDTO
import office.effective.features.user.repository.UsersTagEntity
import office.effective.model.UserModel
import java.util.UUID

class UserDTOModelConverter {

    fun dTOToModel(userDTO: UserDTO): UserModel {
        return UserModel(
            userDTO.fullName,
            UUID.fromString(userDTO.id),
            UsersTagEntity(),
            userDTO.active,
            userDTO.role,
            userDTO.avatarUrl,
            null
        )
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