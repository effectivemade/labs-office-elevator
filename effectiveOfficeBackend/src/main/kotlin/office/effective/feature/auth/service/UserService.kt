package office.effective.feature.auth.service

import office.effective.feature.auth.ITokenVerifier
import office.effective.feature.auth.converters.UserDTOModelConverter
import office.effective.feature.auth.dto.UserDTO
import office.effective.feature.auth.repository.UserRepository
import office.effective.model.UserModel
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

    override fun alterUser(user: UserDTO, token: String): UserDTO {
        TODO("Not yet implemented")
    }

    override fun getUserByToken(tokenStr: String): UserDTO {
        val userEmail = verifier.isCorrectToken(tokenStr as String)
        val userModel = repository.findByEmail(userEmail)
        return converterDTO.modelToDTO(userModel)
    }
}