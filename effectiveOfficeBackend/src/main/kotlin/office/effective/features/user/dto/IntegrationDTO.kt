package office.effective.features.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class IntegrationDTO(
    val id: String,
    val name: String,
    val value: String
)
