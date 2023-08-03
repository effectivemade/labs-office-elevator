package office.effective.common.exception

import java.util.*
import kotlin.reflect.KClass

class InstanceNotFoundException(targetClass: KClass<*>, message: String, id: UUID? = null): RuntimeException(message) {
}