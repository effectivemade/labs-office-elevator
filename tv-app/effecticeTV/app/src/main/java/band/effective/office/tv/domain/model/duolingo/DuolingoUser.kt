package band.effective.office.tv.domain.model.duolingo

import band.effective.office.tv.network.duolingo.models.DuolingoResponse

data class DuolingoUser(
    val username: String,
    val totalXp: Int,
    val photo: String,
    val streakDay: Int
)

fun DuolingoResponse.toDomain(): DuolingoUser {
    val user = users.first()
    return DuolingoUser(
        username = user.name,
        totalXp = user.totalXp,
        photo = parseUrlForDuolingoAvatar(user.picture),
        streakDay = user.streak
    )
}
fun parseUrlForDuolingoAvatar(url: String) = "https:$url/xxlarge"