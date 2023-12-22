package band.effective

object AppConstants {
    val DEFAULT_APP_EMAIL = System.getenv("DEFAULT_APP_EMAIL")
        ?: throw Exception("Environment does not contain default gmail value")
    val JSON_GOOGLE_CREDENTIALS: String = System.getenv("JSON_GOOGLE_CREDENTIALS")
        ?: throw Exception("Environment does not contain google credentials json")
    val APPLICATION_URL: String = System.getenv("APPLICATION_URL")
        ?: throw Exception("Environment does not contain application URL")
    val TEST_APPLICATION_URL: String = System.getenv("TEST_APPLICATION_URL")
        ?: throw Exception("Environment does not contain test application URL")
    val TEST_CALENDARS: List<String> = System.getenv("TEST_CALENDARS").split(" ")
}