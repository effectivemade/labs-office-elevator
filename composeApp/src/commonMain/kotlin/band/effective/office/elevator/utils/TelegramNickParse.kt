package band.effective.office.elevator.utils

fun telegramNickParse(telegramURL: String): String {
    val index = telegramURL.indexOfLast { it == '/' }
    return telegramURL.substring(index)
}