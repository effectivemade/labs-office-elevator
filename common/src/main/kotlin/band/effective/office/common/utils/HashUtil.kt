package band.effective.office.common.utils

import java.security.MessageDigest

object HashUtil {

    fun sha256(value: String?, password: String?): String {
        val bytes =
            MessageDigest.getInstance("SHA-256").digest(value.plus(password).toByteArray())
        return bytes.toHex()
    }

    private fun ByteArray.toHex(): String {
        return joinToString("") { "%02x".format(it) }
    }
}