package band.effective.office.elevator.ui.profile.domain.models

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class User(
    val id: String,
    val imageUrl: String?,
    val userName: String?,
    val post:String?,
    val phoneNumber:String?,
    val telegram: String?,
    val email:String?
): Parcelable
