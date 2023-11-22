package office.effective.features.simpleAuth.service

import io.ktor.server.application.*

/**
 * Interface presents methods to verify tokens
 * */
interface ITokenAuthorizer {
    /**
     * @param call [ApplicationCall] which contains token to verify
     * @author Kiselev Danil
     * */
    suspend fun isCorrectToken(call: ApplicationCall): Boolean;
    /**
     * Set up next handler in chain of responsibility
     *
     * @param handler [ITokenAuthorizer] verifier to be next in chain of responsibility
     * @author Kiselev Danil
     * */
    fun setNext(handler : ITokenAuthorizer?);

}
