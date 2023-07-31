package office.effective.features.user.converters

import office.effective.features.user.dto.UserDTO
import office.effective.features.user.repository.UserRepository
import office.effective.features.user.repository.UsersTagEntity
import office.effective.model.IntegrationModel
import office.effective.model.UserModel
import org.koin.core.context.GlobalContext
import java.util.UUID

class UserDTOModelConverter {
    val repository: UserRepository = GlobalContext.get().get()

    fun dTOToModel(userDTO: UserDTO): UserModel {
        val userId = UUID.fromString(userDTO.id);
        val tag: UsersTagEntity? = repository.findTagByUserOrNull(userId);
        val integrations: Set<IntegrationModel>? = repository.findSetOfIntegrationsByUserOrNull(userId);
        return UserModel(
            userDTO.fullName,
            userId,
            tag ?: UsersTagEntity(),
            userDTO.active,
            userDTO.role,
            userDTO.avatarUrl,
            integrations
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