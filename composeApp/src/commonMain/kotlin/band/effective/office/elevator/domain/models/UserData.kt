package band.effective.office.elevator.domain.models

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
class UserData(
    var phoneNumber: String = "",
    var post: String? = "",
    var name: String = "",
    var telegramNick: String = "",
    var email: String = "",
    var imageUrl: String? = "",
    var idToken: String = ""
) : Parcelable