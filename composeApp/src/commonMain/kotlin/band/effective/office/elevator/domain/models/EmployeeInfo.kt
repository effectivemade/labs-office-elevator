package band.effective.office.elevator.domain.models

import band.effective.office.elevator.MainRes.strings.map
import band.effective.office.elevator.utils.map
import band.effective.office.network.dto.UserDTO
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class EmployeeInfo(
    val id: String,
    val name: String,
    val post: String,
    val state: String,
    val logoUrl: String,
    val phoneNum: String,
    val eMail: String,
    val telegramProfile: String
) : Parcelable{
    companion object {
        val defaultEmployee =
            EmployeeInfo(
                id = "1B",
                name = "Romanov Roman",
                post = "IOS Developer",
                state = "In office",
                logoUrl  = "https://wampi.ru/image/R9C6OC7",
                phoneNum = "+79137894523",
                eMail = "employee@effective.com",
                telegramProfile = "@Romanov_Roman"
            )
    }
}

fun UserDTO.toEmployeeInfo() =
    EmployeeInfo(
        id = id,
        name = fullName,
        post = role,
        state = "",
        logoUrl = avatarUrl,
        phoneNum = integrations?.find { it.name == "phoneNumber" }?.value?:"None",
        eMail = integrations?.find { it.name == "email" }?.value?:"None",
        telegramProfile = integrations?.find { it.name == "telegram" }?.value?:"None",
    )