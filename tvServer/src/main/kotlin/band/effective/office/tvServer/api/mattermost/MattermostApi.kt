package band.effective.office.tvServer.api.mattermost

import band.effective.office.tvServer.api.mattermost.model.*

interface MattermostApi {
    suspend fun getPost(postId: String): GetPostRespond
    suspend fun createPost(request: CreatePostRequest): CreatePostResponse
    suspend fun deletePost(postId: String): DeleteResponse
    suspend fun updatePost(postId: String, request: UpdadeRequest): CreatePostResponse
    suspend fun getUser(userId: String): GetUserResponse
}