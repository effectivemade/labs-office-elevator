package office.effective.common.exception

import java.lang.RuntimeException

class UserIntegrationNotFoundException(message: String) : RuntimeException(message) {
}