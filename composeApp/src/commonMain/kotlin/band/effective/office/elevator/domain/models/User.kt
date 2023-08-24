package band.effective.office.elevator.domain.models

import band.effective.office.elevator.scheme.ProfileData
import band.effective.office.network.dto.IntegrationDTO
import band.effective.office.network.dto.UserDTO

data class User(
    val id: String,
    val imageUrl: String,
    val userName: String,
    val post:String,
    val phoneNumber:String,
    val telegram: String,
    val email:String
){
    companion object {
        val defaultUser =
            User(
                id = "",
                imageUrl = "",
                userName = "",
                post = "",
                phoneNumber = "",
                telegram = "",
                email = ""
            )
    }
}

// TODO(Artem Gruzdev) ID for integrations ??????
fun User.toUserDTO(): UserDTO =
    UserDTO(
        id = id,
        fullName = userName,
        avatarUrl = imageUrl,
        role = post,
        integrations = listOf(
            IntegrationDTO(id = "", name = "email", value = email ),
            IntegrationDTO(id = "", name = "phoneNumber", value = phoneNumber ),
            IntegrationDTO(id = "", name = "telegram", value = telegram ),
            ),
        active = true
    )
fun UserDTO.toUser() =
    User(
        id = id,
        imageUrl = avatarUrl,
        userName = fullName,
        post = role,
        phoneNumber = integrations?.find { it.name == "phoneNumber" }?.value?:"None",
        email = integrations?.find { it.name == "email" }?.value?:"None",
        telegram = integrations?.find { it.name == "telegram" }?.value?:"None",
    )

fun ProfileData.toUser() =
    User(
        id = id,
        imageUrl = imageUrl,
        post = post,
        phoneNumber = phoneNumber,
        email = email,
        telegram = telegramNick,
        userName = name
    )

