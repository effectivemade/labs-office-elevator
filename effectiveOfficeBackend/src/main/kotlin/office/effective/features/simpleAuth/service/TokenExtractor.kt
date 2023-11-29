package office.effective.features.simpleAuth.service

import io.ktor.server.application.*
import io.ktor.server.auth.*
import org.slf4j.LoggerFactory

class TokenExtractor {

    /**
     * @param call [ApplicationCall] incoming call
     * @return token from Bearer Auth header or null, if
     * @author Danil Kiselev
     * */
    fun extractToken(call: ApplicationCall): String? {
        val logger = LoggerFactory.getLogger(this::class.java)
        return call.request.parseAuthorizationHeader()?.render()?.split("Bearer ")?.last() ?: run {
            logger.info("Cannot find auth token")
            return null
        }
    }
}
