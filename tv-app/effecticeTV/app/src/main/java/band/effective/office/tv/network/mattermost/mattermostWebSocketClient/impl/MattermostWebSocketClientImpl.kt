package band.effective.office.tv.network.mattermost.mattermostWebSocketClient.impl

import android.util.Log
import band.effective.office.tv.BuildConfig
import band.effective.office.tv.domain.botLogic.model.BotEvent
import band.effective.office.tv.network.mattermost.MattermostApi
import band.effective.office.tv.network.mattermost.mattermostWebSocketClient.MattermostWebSocketClient
import band.effective.office.tv.network.mattermost.model.*
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import javax.inject.Inject

class MattermostWebSocketClientImpl @Inject constructor(
    private val client: OkHttpClient,
    private val api: MattermostApi
) :
    MattermostWebSocketClient {
    val moshi = Moshi.Builder().build()
    var webSocket: WebSocket? = null
    var lastSeq = 0
    var userId: String? = null
    var eventHandler: (BotEvent) -> Unit = {}

    private class MattermostWebSocketListener(private val handler: (String) -> Unit) :
        WebSocketListener() {
        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            handler(text)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Log.e("socket", t.message ?: "error")
        }
    }

    override fun connect() {
        CoroutineScope(Dispatchers.IO).launch {
            userId = api.auth().id
        }
        webSocket = client.newWebSocket(Request.Builder()
            .url("ws://${BuildConfig.apiMattermostUrl}websocket").build(),
            MattermostWebSocketListener { handler(it) })

        webSocket!!.send(
            moshi.adapter(WebSocketAuthJson::class.java).toJson(WebSocketAuthJson.default)
        )
    }

    private fun handler(response: String) {
        val correctResponse =
            response.replace("\\", "").replace("\"{", "{").replace("}\"", "}")
                .replace("seq_reply", "seq").replace("\"[", "[").replace("]\"", "]")
        when {
            response.contains("posted") -> {
                val adapter = moshi.adapter(PostJson::class.java)
                val post = adapter.fromJson(correctResponse)
                Log.i("socket", post.toString())
                if (post != null && userId != null && post.data.post.userId != userId) {
                    eventHandler(
                        BotEvent.PostMessage(
                            message = post.data.post.message,
                            messageId = post.data.post.id,
                            userName = post.data.senderName,
                            userId = post.data.post.userId,
                            rootId = post.data.post.rootId,
                            channelId = post.data.post.channelId
                        )
                    )
                }
            }
            response.contains("reaction_added") -> {
                val adapter = moshi.adapter(ReactionJson::class.java)
                val reaction = adapter.fromJson(correctResponse)
                Log.i("socket", reaction.toString())
                if (reaction != null && userId != null && reaction.data.reaction.userId != userId) {
                    eventHandler(
                        BotEvent.Reaction(
                            messageId = reaction.data.reaction.postId,
                            userId = reaction.data.reaction.userId,
                            rootId = reaction.data.reaction.postId,
                            channelId = reaction.data.reaction.channelId,
                            emojiName = reaction.data.reaction.emojiName
                        )
                    )
                }
            }
            else -> {
                Log.i("socket", response)
                lastSeq =
                    moshi.adapter(OtherJson::class.java).fromJson(correctResponse)?.seq
                        ?: lastSeq
            }
        }
    }

    override suspend fun postMessage(channelId: String, message: String, root: String) {
        api.createPost(PostMessageData(channelId, message, root))
    }

    override fun disconnect() {
        webSocket?.close(1000, null)
    }

    override fun subscribe(handler: (BotEvent) -> Unit) {
        eventHandler = handler
    }
}