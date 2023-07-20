package office.effective.feature.auth.service

import office.effective.feature.auth.ITokenVerifier
import office.effective.feature.auth.converters.UserDTOModelConverter
import office.effective.feature.auth.dto.UserDTO
import office.effective.feature.auth.repository.UserRepository
import office.effective.model.UserModel
import org.koin.core.context.GlobalContext

class UserService() : IUserService {

    private val verifier: ITokenVerifier = GlobalContext.get().get()
    private val converterDTO: UserDTOModelConverter = GlobalContext.get().get()
    private val repository: UserRepository = GlobalContext.get().get()

    override fun getUsersByTag(): Set<UserDTO> {
        TODO("Not yet implemented")
    }

    override fun getUserById(): UserDTO {
        TODO("Not yet implemented")
    }

    override fun alterUser(): UserDTO {
        TODO("Not yet implemented")
    }

    override fun getUserByToken(tokenStr: String): UserDTO {
        val userEmail = verifier.isCorrectToken(tokenStr as String)
        val userModel = repository.findByEmail(userEmail)
        return converterDTO.modelToDTO(userModel)
    }
}