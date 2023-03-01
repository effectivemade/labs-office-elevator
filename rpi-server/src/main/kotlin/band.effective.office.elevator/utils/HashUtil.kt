package band.effective.office.elevator.utils

import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest
import java.util.*

object HashUtil {

    fun sha256(value: String?): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(value.plus(readFromProperties()).toByteArray())
        return bytes.toHex()
    }

    fun ByteArray.toHex(): String {
        return joinToString("") { "%02x".format(it) }
    }

    private fun readFromProperties(): String? {
        val file = File("/home/ubuntu/elevator/keystore/elevator.properties")
        val prop = Properties()
        FileInputStream(file).use { prop.load(it) }
        return prop.getProperty("password")
    }
}