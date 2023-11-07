package office.effective.features.simpleAuth

/**
 * Interface presents methods to verify tokens
 * */
interface ITokenVerifier {
    /**
     * @param tokenString [String] which contains token to verify
     * @author Kiselev Danil
     * */
    suspend fun isCorrectToken(tokenString: String): String;
}