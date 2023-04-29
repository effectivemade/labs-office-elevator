package band.effective.office.tv.network.duolingo

import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.core.network.entity.ErrorReason
import band.effective.office.tv.network.duolingo.models.DuolingoResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface DuolingoApi {
    @GET("users?fields=users{courses,creationDate,currentCourse," +
            "currentCourseId,globalAmbassadorStatus,hasPlus,id," +
            "lingots,name,picture,privacySettings,roles,streak," +
            "streakData{currentStreak,previousStreak},totalXp,username")
    @Headers("User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/111.0")
    suspend fun getUserInfo(@Query("username") username: String): Either<ErrorReason, DuolingoResponse>
}