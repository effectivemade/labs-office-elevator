package band.effective.office.elevator.data.models.sbp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SBPResponse(
    val version: String,
    @SerialName("dictionary")
    val banksInfo: List<SBPBank>
)

@Serializable
data class SBPBank(
    val bankName: String,
    val logoURL: String,
    val schema: String,
    @SerialName("package_name")
    val packageName: String? = null,
    val webClientUrl: String? = null,
    val isWebClientActive: String? = null
)
