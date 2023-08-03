package office.effective.common.exception

import java.lang.RuntimeException

class ValidationException(message: String): RuntimeException(message) {
}