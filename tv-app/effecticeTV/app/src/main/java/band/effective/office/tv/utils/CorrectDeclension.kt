package band.effective.office.tv.utils

fun getCorrectDeclension(
    number: Int, nominativeCase: String, genitive: String, genetivePlural: String
): String = if (number in 10..20) genetivePlural
else when (number % 10) {
    0 -> genetivePlural
    1 -> nominativeCase
    2, 3, 4 -> genitive
    else -> genetivePlural
}