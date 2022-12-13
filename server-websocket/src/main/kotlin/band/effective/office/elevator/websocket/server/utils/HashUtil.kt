package band.effective.office.elevator.websocket.server.utils

import java.security.MessageDigest

object HashUtil {

    private val passphrase = "password"

    fun sha256(value: String?): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(value.plus(passphrase).toByteArray())
        return bytes.toHex()
    }

    fun ByteArray.toHex(): String {
        return joinToString("") { "%02x".format(it) }
    }
}