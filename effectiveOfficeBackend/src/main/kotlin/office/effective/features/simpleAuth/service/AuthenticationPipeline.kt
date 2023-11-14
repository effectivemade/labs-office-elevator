package office.effective.features.simpleAuth.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory


class AuthenticationPipeline(list: List<ITokenVerifier>) {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private val firstNode: ITokenVerifier
    private var previousNode: ITokenVerifier? = null

    init {
        for (node in list) {
            if (previousNode == null) {
                previousNode = node
            } else {
                previousNode?.setNext(node)
                previousNode = node
            }
        }
        if (list.isEmpty()) {
            logger.error("Empty verifier list")
            throw RuntimeException("Empty verifier list");
        }
        firstNode = list.first()
    }

    suspend fun authenticateToken(token: String): Boolean {
        return firstNode.isCorrectToken(token)
    }
}
