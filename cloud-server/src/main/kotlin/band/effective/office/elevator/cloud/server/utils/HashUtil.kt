package band.effective.office.elevator.cloud.server.utils

import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest
import java.util.*

object HashUtil {

    fun sha256(value: String?): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(value.plus(readFromProperties("password")).toByteArray())
        return bytes.toHex()
    }

    fun ByteArray.toHex(): String {
        return joinToString("") { "%02x".format(it) }
    }
}