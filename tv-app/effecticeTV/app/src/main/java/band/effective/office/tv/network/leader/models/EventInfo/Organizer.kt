package band.effective.office.tv.network.leader.models.EventInfo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Organizer(
    val company: Any?,
    val companyId: Any?,
    val email: String,
    val id: Int,
    val internationalPhone: Boolean,
    val name: String,
    val phone: String,
    val photo: String,
    val photo_180: String,
    val photo_360: String,
    val photo_520: String,
    val photos: Any?,
    val position: String,
    val telegram: Any?,
    val url: Any?,
    val userId: Int
)