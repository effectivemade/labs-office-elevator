package office.effective.feature.auth.repository

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.uuid
import org.ktorm.schema.varchar
import java.util.UUID

interface UserTagEntity : Entity<UserTagEntity> {
    val id: UUID
    val name: String
}

object UserTags : Table<UserTagEntity>("user_tags") {
    val id = uuid("id").bindTo { it.id }.primaryKey()
    val name = varchar("name").bindTo { it.name }
}