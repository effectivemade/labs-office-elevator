package office.effective.features.user

interface ITokenVerifier {
    fun isCorrectToken(tokenString: String): String;
}