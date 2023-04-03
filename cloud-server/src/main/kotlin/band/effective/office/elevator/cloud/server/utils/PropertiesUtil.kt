package band.effective.office.elevator.cloud.server.utils

object PropertiesUtil {
    fun read(key: String): String? {
        return System.getenv(key)
    }
}
