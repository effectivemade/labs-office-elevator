package band.effective.office.tv.domain.model.duolingo

import band.effective.office.tv.network.duolingo.models.DuolingoResponse

data class DuolingoUser(
    val username: String,
    val totalXp: Int,
    val photo: String
)

fun DuolingoResponse.toDomain(): DuolingoUser {
    val user = users.first()
    return DuolingoUser(
        username = user.username,
        totalXp = user.totalXp,
        photo = parseUrlForDuolingoAvatar(user.picture)
    )
}
fun parseUrlForDuolingoAvatar(url: String) = "https:$url/xxlarge"