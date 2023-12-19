package office.effective.common.constants

/**
 * Constants that can be used anywhere in the application
 */
object GlobalConstants {
    /**
    * Indicates whether to use a debug configuration
    *
    * Any value instead of "true" will be recognised as "false"
    */
    val USE_DEBUG_CONFIGURATION = System.getenv("USE_DEBUG_CONFIGURATION").equals("true")
}