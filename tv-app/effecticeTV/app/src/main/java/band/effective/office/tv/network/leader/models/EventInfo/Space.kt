package band.effective.office.tv.network.leader.models.EventInfo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Space(
    val active: Boolean,
    val address: Address,
    val addressId: Int,
    val agenda: List<String>,
    val createdAt: String,
    val description: String,
    val email: String,
    val id: Int,
    val kworkingState: Any?,
    val minimalPeriod: Int,
    val name: String,
    val phone: String,
    val phoneExtension: Any?,
    val photos: List<PhotoX>,
    val rating: Any?,
    val restrictEventOwnerOfflineConfirm: Boolean,
    val scheduleOnRequest: Boolean,
    val socialNetworks: List<SocialNetwork>,
    val square: String,
    val stat: Stat,
    val tags: List<String>,
    val type: String,
    val updatedAt: String
)