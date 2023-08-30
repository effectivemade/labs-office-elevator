package office.effective.features.user

/**
 * Interface presents methods to verify tokens
 * */
interface ITokenVerifier {
    /**
     * @param tokenString [String] which contains token to verify
     * @author Kiselev Danil
     * */
    fun isCorrectToken(tokenString: String): String;
}