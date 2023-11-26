package office.effective.features.simpleAuth.service

import io.ktor.server.application.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class AuthorizationPipeline(private val iterable: Iterable<ITokenAuthorizer>?) : ITokenAuthorizer {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private val authorizers: MutableCollection<ITokenAuthorizer>?


    init {
        //TODO Make correct Builder
        authorizers = iterable?.toMutableList()
    }

    override suspend fun isCorrectToken(call: ApplicationCall): Boolean {
        iterable?.forEach {
            if (it.isCorrectToken(call)) {
                return@isCorrectToken true
            }
        }?: logger.warn("Authorizers collection is empty")
        return false
    }

    fun addAuthorizer(authorizer: ITokenAuthorizer?) : AuthorizationPipeline {
        if(authorizer != null) {
            authorizers?.add(authorizer)?: mutableListOf(authorizer)
        }
        return this
    }

}
