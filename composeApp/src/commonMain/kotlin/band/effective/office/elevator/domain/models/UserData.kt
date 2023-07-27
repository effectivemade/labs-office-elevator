package band.effective.office.elevator.domain.models

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

sealed class User : Parcelable {

    @Parcelize
    data class UserData(
        val phoneNumber: String = "",
        val post: String? = "",
        val name: String = "",
        val email: String = "",
        val imageUrl: String? = ""
    ) : User()
}