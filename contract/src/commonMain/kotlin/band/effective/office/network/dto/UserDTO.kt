package band.effective.office.network.dto

data class UserDTO(
    val active: Boolean,
    val avatarUrl: String,
    val fullName: String,
    val id: String,
    val integrations: List<IntegrationDTO>,
    val role: String
)