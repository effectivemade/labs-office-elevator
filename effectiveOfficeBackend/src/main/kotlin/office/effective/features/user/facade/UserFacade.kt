package office.effective.features.user.facade

import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.utils.DatabaseTransactionManager
import office.effective.features.user.ITokenVerifier
import office.effective.features.user.converters.UserDTOModelConverter
import office.effective.dto.UserDTO
import office.effective.features.user.service.IUserService
import office.effective.model.UserModel

class UserFacade(
    private val service: IUserService,
    private val converterDTO: UserDTOModelConverter,
    private val verifier: ITokenVerifier,
    private val transactionManager: DatabaseTransactionManager
) {
    fun getUsersByTag(tagStr: String): Set<UserDTO> {
        val models: Set<UserModel> =
            transactionManager.useTransaction<Set<UserModel>>({ service.getUsersByTag(tagStr) })
        val dtos: MutableSet<UserDTO> = mutableSetOf()
        models.forEach { dtos.add(converterDTO.modelToDTO(it)) }
        return dtos.toSet()
    }

    /**
     * Retrieves all users
     * @return Set<UserDTO>
     *
     * @author Daniil Zavyalov
     * */
    fun getUsers(): Set<UserDTO> {
        return transactionManager.useTransaction({
            val dtos: MutableSet<UserDTO> = mutableSetOf()
            for (model in service.getAll()) {
                dtos.add(converterDTO.modelToDTO(model))
            }
            dtos.toSet()
        })
    }

    fun getUserById(userIdStr: String): UserDTO {
        return transactionManager.useTransaction<UserDTO>({
            converterDTO.modelToDTO(
                service.getUserById(userIdStr) ?: throw InstanceNotFoundException(
                    UserModel::class, "User with id ${userIdStr} not found", null
                )
            )
        })
    }

    /**
     * Retrieves a user model by email
     *
     * @author Daniil Zavyalov
     */
    fun getUserByEmail(email: String): UserDTO {
        return transactionManager.useTransaction<UserDTO>({
            converterDTO.modelToDTO(
                service.getUserByEmail(email) ?: throw InstanceNotFoundException(
                    UserModel::class, "User with email $email not found", null
                )
            )
        })
    }

    /**
     * Updates a given user. Use the returned model for further operations
     *
     * @author Danil Kiselev
     */
    fun updateUser(user: UserDTO): UserDTO {
        return transactionManager.useTransaction({
            converterDTO.modelToDTO(
                service.updateUser(
                    converterDTO.dTOToModel(user)
                )
            )
        })
    }

//    fun getUserByToken(tokenStr: String): UserDTO {
//        val userEmail = verifier.isCorrectToken(tokenStr)
//        return converterDTO.modelToDTO(transactionManager.useTransaction<UserModel>({ service.getUserByEmail(userEmail) }))
//    }
}