package band.effective.office.tablet.utils

object PropertiesUtil {
    fun read(key: String): String? {
        return System.getenv(key)
    }
}
