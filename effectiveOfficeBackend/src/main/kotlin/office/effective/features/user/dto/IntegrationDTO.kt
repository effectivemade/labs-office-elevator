package office.effective.features.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class IntegrationDTO(
    val name: String,
    val value: String
)
