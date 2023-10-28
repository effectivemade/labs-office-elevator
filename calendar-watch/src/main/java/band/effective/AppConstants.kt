package band.effective

object AppConstants {
    val DEFAULT_APP_EMAIL = System.getenv("DEFAULT_APP_EMAIL")
        ?: throw Exception("Environment does not contain default gmail value")
    val JSON_GOOGLE_CREDENTIALS: String = System.getenv("JSON_GOOGLE_CREDENTIALS")
    val APPLICATION_URL: String = System.getenv("APPLICATION_URL")
}