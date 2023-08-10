package office.effective.features.simpleAuth.repository

import office.effective.common.exception.InstanceNotFoundException
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.find

class AuthRepository(private val db: Database) {
    fun findApiKey(keyEncrypted: String):String{
        return db.apikeys.find { it.value.eq(keyEncrypted)  }?.keyValue ?: throw InstanceNotFoundException(ApiKeyEntity::class, "No such key found")
    }
}