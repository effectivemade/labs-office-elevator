package office.effective.features.simpleAuth.service

import io.ktor.server.application.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Encapsulate pipeline of authorizers
 * */
class AuthorizationPipeline : ITokenAuthorizer {

    val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private val authorizers: MutableCollection<ITokenAuthorizer> = mutableListOf()

    override suspend fun isCorrectToken(call: ApplicationCall): Boolean {

        if (authorizers.isEmpty()) {
            logger.warn("Authorizers collection is empty")
            return false
        }

        authorizers.forEach {
            if (it.isCorrectToken(call)) {
                return@isCorrectToken true
            }
        }
        return false
    }

    /**
     * Allow you to add authorizer to pipeline
     * @param authorizer [ITokenAuthorizer]
     * @return [AuthorizationPipeline] - instance of pipeline
     * @author Danil Kiselev
     * */
    fun addAuthorizer(authorizer: ITokenAuthorizer): AuthorizationPipeline {
        authorizers.add(authorizer)
        return this
    }

}
