package band.effective.mattermost

import band.effective.core.Either
import band.effective.core.ErrorReason
import band.effective.mattermost.models.response.models.EmojiInfoForApi
import band.effective.mattermost.models.response.ResponseGetPostsForChannel
import band.effective.mattermost.models.response.UserInfoResponse
import band.effective.mattermost.models.response.models.Channel
import band.effective.mattermost.models.response.models.EmojiInfo
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface MattermostApi {

    @GET("users/me/channels")
    @Headers(
            "X-Requested-With: XMLHttpRequest",
            "Content-Typ: application/json"
    )
    suspend fun getChannelMattermost(
            @Header("Authorization") token: String
    ): Either<ErrorReason, List<Channel>>

    @GET("channels/{channelId}/posts")
    @Headers(
            "X-Requested-With: XMLHttpRequest",
            "Content-Typ: application/json"
    )
    suspend fun getPostsFromChannel(
            @Header("Authorization") token: String,
            @Path("channelId") channelId: String,
            @Query("since") sinceTime: Long
    ): Either<ErrorReason, ResponseGetPostsForChannel>

    @GET("files/{fileId}")
    @Headers(
            "X-Requested-With: XMLHttpRequest",
            "Content-Typ: application/json"
    )
    suspend fun downloadFile(
            @Header("Authorization") token: String,
            @Path("fileId") fileId: String
    ): Response<ResponseBody>

    @POST("reactions")
    suspend fun makeReaction(
        @Header("Authorization") token: String,
        @Body emojiInfo: EmojiInfoForApi
    ): Either<ErrorReason, EmojiInfoForApi>

    @GET("users/me")
    @Headers(
            "X-Requested-With: XMLHttpRequest",
            "Content-Typ: application/json"
    )
    suspend fun getUserInfoFromToken(@Header("Authorization") token: String): Either<ErrorReason, UserInfoResponse>
}