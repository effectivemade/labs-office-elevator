package office.effective.features.simpleAuth

/**
 * Interface presents methods to verify tokens
 * */
interface ITokenVerifier {
    /**
     * @param tokenString [String] which contains token to verify
     * @author Kiselev Danil
     * */
    suspend fun isCorrectToken(tokenString: String): Boolean;
    /**
     * Set up next handler in chain of responsibility
     *
     * @param handler [ITokenVerifier] verifier to be next in chain of responsibility
     * @author Kiselev Danil
     * */
    fun setNext(handler : ITokenVerifier?);

    /**
     * Call next handler in chain
     *
     * @param tokenString [String] which contains token to verify
     * @author Kiselev Danil
     * */
    suspend fun next(tokenString: String): Boolean;
}
