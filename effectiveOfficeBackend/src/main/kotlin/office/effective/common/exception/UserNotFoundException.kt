package office.effective.common.exception

import java.lang.RuntimeException

class UserNotFoundException(message: String) : RuntimeException(message) {
}