package office.effective.features.simpleAuth.service

import io.ktor.server.application.*
import io.ktor.server.request.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Authorizer to permit routes. Permit all child routes, if parent permitted.
 * Example: "/workspaces" permission allow access to "/workspaces/zones" routes.
 * @author Danil Kiselev
 * */
class PermitAuthorizer(private val permittedPaths: Iterable<String>) : Authorizer {

    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override suspend fun authorize(call: ApplicationCall): Boolean {
        val fixedPathAsArray = call.request.path().split('/').filter { it.isNotBlank() }
        for (permitted in permittedPaths) {
            val permittedPathAsArray = permitted.split('/').filter { it.isNotBlank() }
            if (permittedPathAsArray.size <= fixedPathAsArray.size) {
                for (i in permittedPathAsArray.indices) {
                    if (permittedPathAsArray[i] == fixedPathAsArray[i]) {
                        return true
                    }
                }
            }
        }
        return false
    }


}
