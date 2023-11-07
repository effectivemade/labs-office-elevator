package office.effective.features.simpleAuth

import office.effective.common.exception.InstanceNotFoundException
import office.effective.features.simpleAuth.repository.AuthRepository
import org.koin.core.context.GlobalContext
import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter

/**
 * [ITokenVerifier] implementation. Check api keys
 * */
class ApiKeyVerifier : ITokenVerifier {
    /**
     * Check api key from input line. String encrypting by SHA-256 and comparing with encrypted keys from database. If it cannot find one, throw [InstanceNotFoundException]
     * @param tokenString [String] which contains token to verify
     * @return random String
     * @author Kiselev Danil
     */
    override suspend fun isCorrectToken(tokenString: String): Boolean {
        val repository : AuthRepository = GlobalContext.get().get()
        try {
            val key = repository.findApiKey(encryptKey("SHA-256", tokenString))
        }
        catch(ex: InstanceNotFoundException){
            return next(tokenString)
        }
        return true
    }

    private var nextHandler: ITokenVerifier? = null;
    override fun setNext(handler: ITokenVerifier?) {
        this.nextHandler = handler
    }

    override suspend fun next(tokenString: String): Boolean {
        return nextHandler?.isCorrectToken(tokenString) ?: return false;
    }

    /**
     * Use to encrypt api key before check it in database
     *
     * @param type [String] hash function type
     * @param input [String] input line
     * @return encrypted [String]
     * */
    private fun encryptKey(type: String, input: String): String{
            val bytes = MessageDigest
                .getInstance(type)
                .digest(input.toByteArray())
            return DatatypeConverter.printHexBinary(bytes).uppercase()
    }
}