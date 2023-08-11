package office.effective.features.user.converters

import office.effective.common.utils.UuidValidator
import office.effective.features.user.dto.IntegrationDTO
import office.effective.features.user.dto.UserDTO
import office.effective.features.user.repository.UserRepository
import office.effective.features.user.repository.UsersTagEntity
import office.effective.model.IntegrationModel
import office.effective.model.UserModel
import org.koin.core.context.GlobalContext
import java.util.UUID

class UserDTOModelConverter(
    private val repository: UserRepository,
    private val converter: IntegrationDTOModelConverter,
    private val uuidConverter : UuidValidator
) {

    fun dTOToModel(userDTO: UserDTO): UserModel {
        val userId = uuidConverter.uuidFromString(userDTO.id)
        val tag: UsersTagEntity? = repository.findTagByUserOrNull(userId);
        val integrations: MutableSet<IntegrationModel> = mutableSetOf()
        userDTO.integrations?.forEach { integrations.add(converter.dTOToModel(it)) }
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

        val integrations: MutableList<IntegrationDTO> = mutableListOf()
        userModel.integrations?.forEach { integrations.add(converter.modelToDTO(it)) }
        return UserDTO(
            id = userModel.id.toString(),
            fullName = userModel.fullName,
            active = userModel.active,
            role = userModel.role ?: "",
            avatarUrl = userModel.avatarURL ?: "",
            integrations = integrations
        )
    }


}