package office.effective.features.simpleAuth.service

import io.ktor.server.application.*
import io.ktor.server.auth.*
import org.slf4j.LoggerFactory

class TokenExtractor {

    fun extractToken(call: ApplicationCall): String? {
        val logger = LoggerFactory.getLogger(this::class.java)
        return call.request.parseAuthorizationHeader()?.render()?.split("Bearer ")?.last() ?: run {
            logger.info("Verification failed. Cannot find auth token")
            return null
        }
    }
}
