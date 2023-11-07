package office.effective.features.simpleAuth.service


import office.effective.features.simpleAuth.ITokenVerifier

class AuthenticationPipeline(list: List<ITokenVerifier>) {
    var firstNode: ITokenVerifier? = null

    init {
        for (node in list) {
            if (firstNode == null) {
                firstNode = node
            } else {
                firstNode?.setNext(node)
                firstNode = node
            }
        }
        firstNode = list.first()
    }

    suspend fun authenticateToken(token: String): Boolean {
        return firstNode?.isCorrectToken(token) ?: false
    }
}
