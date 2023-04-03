package band.effective.office.common.utils

object PropertiesUtil {
    fun read(key: String): String? {
        return System.getenv(key)
    }
}
