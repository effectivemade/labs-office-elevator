package band.effective.office.tv.network.mattermost.mattermostWebSocketClient.impl

import android.util.Log
import band.effective.office.tv.BuildConfig
import band.effective.office.tv.domain.botLogic.model.BotEvent
import band.effective.office.tv.domain.model.message.toBotEvent
import band.effective.office.tv.network.mattermost.MattermostApi
import band.effective.office.tv.network.mattermost.mattermostWebSocketClient.MattermostWebSocketClient
import band.effective.office.tv.network.mattermost.model.*
import com.squareup.moshi.Moshi
import okhttp3.*
import javax.inject.Inject


class MattermostWebSocketClientImpl @Inject constructor(
    private val client: OkHttpClient,
    private val api: MattermostApi,
    private val moshi: Moshi
) : MattermostWebSocketClient {
    private val webSocket by lazy {
        client.newWebSocket(Request.Builder()
            .url("ws://${BuildConfig.apiMattermostUrl}websocket").build(),
            MattermostWebSocketListener { handler(it) })
    }
    private var lastSeq = 0
    lateinit var userId: String
    lateinit var botName: String
    lateinit var eventHandler: (BotEvent) -> Unit

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

    /**Connect with mattermost*/
    override suspend fun connect() {
        val authResponse = api.auth()
        userId = authResponse.id
        botName = authResponse.username
        webSocket.send(
            moshi.adapter(WebSocketAuthJson::class.java).toJson(WebSocketAuthJson.default)
        )
    }

    private fun handler(response: String) {
        when {
            response.isPost() -> {
                val post = moshi.adapter(PostJson::class.java).fromJson(response.correct())
                Log.i("socket", post.toString())
                if (post != null && post.data.post.userId != userId) {
                    eventHandler(post.toBotEvent())
                }
            }
            response.isReaction() -> {
                val reaction = moshi.adapter(ReactionJson::class.java).fromJson(response.correct())
                Log.i("socket", reaction.toString())
                if (reaction != null && reaction.data.reaction.userId != userId) {
                    eventHandler(reaction.toBotEvent())
                }
            }
            else -> {
                Log.i("socket", response)
                lastSeq =
                    moshi.adapter(OtherJson::class.java).fromJson(response.correct())?.seq ?: lastSeq
            }
        }
    }

    /**
     * Send message in chat
     * @param channelId id of the chat you are sending the message
     * @param message text you are sending
     * @param root thread's id*/
    override suspend fun postMessage(channelId: String, message: String, root: String) {
        api.createPost(PostMessageData(channelId, message, root))
    }

    /**Get bot name*/
    override fun name(): String = botName


    /**Disconnect from server*/
    override fun disconnect() {
        webSocket.close(1000, null)
    }

    /**Subscribe on server events
     * @param handler events handler*/
    override fun subscribe(handler: (BotEvent) -> Unit) {
        eventHandler = handler
    }

    private fun String.isPost(): Boolean = contains("posted")

    private fun String.isReaction(): Boolean = contains("reaction_added")

    private fun String.correct(): String =
        replace("\\n", "\n").replace("\\", "").replace("\"{", "{").replace("}\"", "}")
            .replace("seq_reply", "seq").replace("\"[", "[").replace("]\"", "]")
}