package band.effective.office.elevator.websocket.server.utils

import com.soywiz.krypto.encoding.hex
import io.ktor.utils.io.core.*
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

object EncryptionUtils {

    fun encrypt(value: String): String {
        val digest: MessageDigest = MessageDigest.getInstance("SHA-256")
        return digest.digest(value.plus("password").toByteArray(StandardCharsets.UTF_8)).hex
    }
}