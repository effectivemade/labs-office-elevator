package band.effective.office.tv.network.leader.models.EventInfo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    val email: Any?,
    val employment: Any?,
    val employments: List<Any?>,
    val father_name: String,
    val first_name: String,
    val id: Int,
    val last_name: String,
    val phone: Any?,
    val photo: Any?
)