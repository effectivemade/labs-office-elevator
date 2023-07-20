package office.effective.model

import office.effective.feature.auth.repository.UsersTagEntity
import java.util.*

data class UserModel(
    var fullName: String,
    var id: UUID?,
    var tag: UsersTagEntity,
    var active: Boolean,
    var role: String?,
    var avatarURL: String?,
    var integrations: Set<IntegrationModel>?
)