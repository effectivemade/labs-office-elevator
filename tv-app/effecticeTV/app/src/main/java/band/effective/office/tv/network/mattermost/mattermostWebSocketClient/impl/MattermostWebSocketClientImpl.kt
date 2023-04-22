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
                .replace("seq_reply", "seq").replace("\"[","[").replace("]\"","]")
        when {
            response.contains("posted") -> {
                val adapter = moshi.adapter(PostJson::class.java)
                val post = adapter.fromJson(correctResponse)
                Log.i("socket", post.toString())
                if (post != null && userId != null && post.data.post.userId != userId) postMessage(
                    post.data.post.channelId,
                    "Получено сообщение: ${post.data.post.message}",
                    if (post.data.post.rootId == "") post.data.post.id else post.data.post.rootId
                )
            }
            response.contains("reaction_added") -> {
                val adapter = moshi.adapter(ReactionJson::class.java)
                val reaction = adapter.fromJson(correctResponse)
                Log.i("socket", reaction.toString())
                if (reaction != null && userId != null && reaction.data.reaction.userId != userId) postMessage(
                    reaction.data.reaction.channelId,
                    "Получен эмоджи: ${reaction.data.reaction.emojiName}",
                    reaction.data.reaction.postId
                )
            }
            else -> {
                Log.i("socket", response)
                lastSeq =
                    moshi.adapter(OtherJson::class.java).fromJson(correctResponse)?.seq
                        ?: lastSeq
            }
        }
    }

    fun postMessage(channelId: String, message: String, root: String = "") {
        CoroutineScope(Dispatchers.IO).launch {
            api.createPost(PostMessageData(channelId, message, root))
        }
    }

    override fun disconnect() {
        webSocket?.close(1000, null)
    }

    override fun subscribe(handler: (BotEvent) -> Unit) {

    }
}