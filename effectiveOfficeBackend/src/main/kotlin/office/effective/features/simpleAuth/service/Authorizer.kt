package office.effective.features.simpleAuth.service

import io.ktor.server.application.*

/**
 * Interface presents methods to verify tokens
 * */
interface Authorizer {
    /**
     * @param call [ApplicationCall] which contains token to verify
     * @author Kiselev Danil
     * */
    suspend fun isCorrectToken(call: ApplicationCall): Boolean
}
