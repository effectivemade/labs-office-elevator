package office.effective.model

import office.effective.feature.auth.repository.UsersTagEntity
import java.util.*

data class UserModel(var fullName: String) {

    var id: UUID? = null;
    var tag: UsersTagEntity = UsersTagEntity(); // reference, may be list
    var active: Boolean = false;
    var role: String? = null;
    var avatarURL: String? = null;
    var integrations: Set<IntegrationModel>? = null

    constructor(
        _fullName: String,
        _id: UUID?,
        _tag: UsersTagEntity,
        _active: Boolean,
        _role: String?,
        _avatarURL: String?,
        _integrations: Set<IntegrationModel>?
    ) : this(_fullName) {
        this.id = _id;
        this.tag = _tag;
        this.active = _active
        this.role = _role
        this.avatarURL = _avatarURL
        this.integrations = _integrations
    }


}