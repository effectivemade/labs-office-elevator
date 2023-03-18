package band.effective.office.elevator.utils

import java.io.File
import java.io.FileInputStream
import java.util.*

object PropertiesUtil {
    fun read(key: String): String? {
        val file = File("/home/ubuntu/elevator/keystore/elevator.properties")
        val prop = Properties()
        FileInputStream(file).use { prop.load(it) }
        return prop.getProperty(key)
    }
}