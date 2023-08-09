package office.effective.features.user

import office.effective.features.simpleAuth.repository.AuthRepository
import org.koin.core.context.GlobalContext
import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter

class ApiKeyVerifier : ITokenVerifier {
    override fun isCorrectToken(tokenString: String): String {
        val repository : AuthRepository = GlobalContext.get().get()

        val key = repository.findApiKey(encryptKey("SHA-256",tokenString))

        return "!@#$%^&*()"
    }
    private fun encryptKey(type: String, input: String): String{
            val bytes = MessageDigest
                .getInstance(type)
                .digest(input.toByteArray())
            return DatatypeConverter.printHexBinary(bytes).uppercase()
    }
}