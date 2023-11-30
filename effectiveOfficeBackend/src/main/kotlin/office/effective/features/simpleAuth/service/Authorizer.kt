package office.effective.features.simpleAuth.service

import io.ktor.server.application.*

/**
 * Interface for authorization chain of responsibility element
 * */
interface Authorizer {
    /**
     * @param call [ApplicationCall] incoming call to authorize
     * @author Kiselev Danil
     * */
    suspend fun isCorrectToken(call: ApplicationCall): Boolean
}
