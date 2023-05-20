package band.effective.office.tv.network.mattermost

import band.effective.office.tv.network.mattermost.model.AuthJson
import band.effective.office.tv.network.mattermost.model.PostMessageData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MattermostApi {
    @GET("users/me")
    suspend fun auth(): AuthJson

    @POST("posts")
    suspend fun createPost(@Body post: PostMessageData)
}