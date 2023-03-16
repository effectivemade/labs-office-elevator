package band.effective.office.tv.network.leader.models.ErrorNetwork

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Errors(
    val paginationSize: List<String>
)