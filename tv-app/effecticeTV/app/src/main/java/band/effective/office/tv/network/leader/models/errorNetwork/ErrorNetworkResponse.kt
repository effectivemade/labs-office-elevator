package band.effective.office.tv.network.leader.models.errorNetwork

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorNetworkResponse(
    val code: Int? = null,
    val error: Error? = null,
    val errors: Errors? = null,
    val message: String? = null
)