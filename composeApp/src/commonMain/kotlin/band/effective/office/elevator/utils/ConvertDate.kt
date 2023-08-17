package band.effective.office.elevator.utils

fun NumToMonth(month: Int): String {
    return when(month) {
        1 -> "Янв."
        2 -> "Фев."
        3 -> "Мар."
        4 -> "Апр."
        5 -> "Май"
        6 -> "Июнь"
        7 -> "Июль"
        8 -> "Авг."
        9 -> "Сен."
        10 -> "Окт."
        11 -> "Ноя."
        12 -> "Дек."
        else -> "Unknown"
    }
}