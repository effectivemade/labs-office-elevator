package band.effective.office.elevator.cloud.server.utils

import java.io.File
import java.io.FileInputStream
import java.util.Properties

object PropertiesUtil {
    fun read(key: String): String? {
        val file = File("root/keystore/elevator.properties")
        val prop = Properties()
        FileInputStream(file).use { prop.load(it) }
        return prop.getProperty(key)
    }
}
