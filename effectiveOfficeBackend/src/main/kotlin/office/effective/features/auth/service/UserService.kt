package office.effective.features.auth.service

import office.effective.features.auth.ITokenVerifier
import office.effective.features.auth.converters.UserDTOModelConverter
import office.effective.features.auth.dto.UserDTO
import office.effective.features.auth.repository.UserRepository
import org.koin.core.context.GlobalContext
import java.util.*

class UserService() : IUserService {

    private val verifier: ITokenVerifier = GlobalContext.get().get()
    private val converterDTO: UserDTOModelConverter = GlobalContext.get().get()
    private val repository: UserRepository = GlobalContext.get().get()

    override fun getUsersByTag(tagStr: String, token: String): Set<UserDTO>? {
        val models = repository.findByTag(repository.findTagByName(tagStr).id)
        val dtos: MutableSet<UserDTO> = mutableSetOf()
        models.forEach { dtos.add(converterDTO.modelToDTO(it)) }
        return dtos.toSet()
    }

    override fun getUserById(userIdStr: String, token: String): UserDTO {
        return converterDTO.modelToDTO(
            repository.findById(
                UUID.fromString(userIdStr)
            )
        )
    }

    override fun updateUser(user: UserDTO, token: String): UserDTO {
//        TODO fix update user inside repository
//        val model = repository.updateUser(converterDTO.dTOToModel(user))
//        return converterDTO.modelToDTO(model)
        return user
    }

    override fun getUserByToken(tokenStr: String): UserDTO {
        val userEmail = verifier.isCorrectToken(tokenStr as String)
        val userModel = repository.findByEmail(userEmail)
        return converterDTO.modelToDTO(userModel)
    }
}