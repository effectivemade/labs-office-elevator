package office.effective.feature.auth.repository

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.uuid
import org.ktorm.schema.varchar
import java.util.*

interface UserEntity : Entity<UserEntity> {
    companion object : Entity.Factory<UserEntity>()

    val id: UUID;
    val fullname: String
    val tag: UserTagEntity // reference, may be list
    val active: Boolean
    val role: String
    var avatarURL: String
}

object Users : Table<UserEntity>("users") {
    val id = uuid("id").bindTo { it.id }.primaryKey()
    val fullname = varchar("full_name").bindTo { it.fullname }
    val tagId = uuid("tag_id").references(UserTags) { it.tag }//ManyToOne
    val active = boolean("active").bindTo { it.active }
    val role = varchar("role").bindTo { it.role }
    var avatarURL = varchar("avatar_url").bindTo { it.avatarURL }
}