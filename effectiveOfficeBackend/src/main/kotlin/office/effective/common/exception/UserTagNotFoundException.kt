package office.effective.common.exception

import java.lang.RuntimeException

class UserTagNotFoundException(message: String) : RuntimeException(message) {
}