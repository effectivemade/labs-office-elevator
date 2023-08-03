package office.effective.common.utils

import office.effective.common.exception.ValidationException
import java.lang.IllegalArgumentException
import java.util.UUID

class UuidValidator {
    fun uuidFromString(stringId: String): UUID {
        try {
            return UUID.fromString(stringId)
        } catch (ex: IllegalArgumentException) {
            throw ValidationException("Provided id is not UUID: " + ex.message)
        }
    }
}