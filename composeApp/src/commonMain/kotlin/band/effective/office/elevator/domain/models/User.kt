package band.effective.office.elevator.domain.models

import band.effective.office.elevator.scheme.ProfileData
import band.effective.office.network.dto.IntegrationDTO
import band.effective.office.network.dto.UserDTO

data class User(
    val id: String,
    val imageUrl: String,
    val userName: String,
    val post: String,
    val phoneNumber: String,
    val telegram: String,
    val email: String
) {
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

fun User.toUserDTO(idEmail: String, idPhoneNumber: String, idTelegram: String): UserDTO =
    UserDTO(
        id = id,
        fullName = userName,
        active = true,
        role = post,
        avatarUrl = imageUrl,
        integrations = listOf(
            IntegrationDTO(id = idEmail, name = "email", value = email),
            IntegrationDTO(id = idPhoneNumber, name = "phoneNumber", value = phoneNumber),
            IntegrationDTO(id = idTelegram, name = "telegram", value = telegram),
        ),
        email = email,
        tag = "employee"
    )

fun UserDTO.toUser() =
    User(
        id = id,
        imageUrl = avatarUrl,
        userName = fullName,
        post = role,
        phoneNumber = integrations?.find { it.name == "phoneNumber" }?.value
            ?: "".apply { println("Get empty phone number") },
        email = integrations?.find { it.name == "email" }?.value
            ?: "".apply { println("Get empty email") },
        telegram = integrations?.find { it.name == "telegram" }?.value
            ?: "".apply { println("Get empty telegram") }
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

