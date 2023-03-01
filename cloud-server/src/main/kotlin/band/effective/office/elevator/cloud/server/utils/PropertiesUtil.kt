package band.effective.office.elevator.cloud.server.utils

import java.io.File
import java.io.FileInputStream
import java.util.*

fun readFromProperties(key: String): String? {
    val file = File("root/keystore/elevator.properties")
    println("file: ${file.absolutePath}")
    val prop = Properties()
    FileInputStream(file).use { prop.load(it) }
    println("androidClient: ${prop.getProperty("androidClient")}")
    println("webClient: ${prop.getProperty("webClient")}")
    println("iosClient: ${prop.getProperty("iosClient")}")
    return prop.getProperty(key)
}