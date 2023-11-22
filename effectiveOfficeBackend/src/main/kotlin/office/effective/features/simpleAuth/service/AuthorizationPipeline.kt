package office.effective.features.simpleAuth.service

import io.ktor.server.application.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class AuthorizationPipeline(private val iterable: Iterable<ITokenAuthorizer>) : ITokenAuthorizer {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private val authorizers: MutableCollection<ITokenAuthorizer>


    init {
        if (iterable.lastOrNull() == null) {
            logger.error("Empty verifier list")
            throw RuntimeException("Empty verifier list");
        }
        authorizers = iterable.toMutableList()
    }

    override suspend fun isCorrectToken(call: ApplicationCall): Boolean {
        iterable.forEach {
            if (it.isCorrectToken(call)) {
                return@isCorrectToken true
            }
        }
        return false
    }

    override fun setNext(handler: ITokenAuthorizer?) {
        if(handler != null) {
            authorizers.add(handler)
        }
    }

}
