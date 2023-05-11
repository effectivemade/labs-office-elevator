package band.effective

import band.effective.mattermost.MattermostRepositoryImpl
import band.effective.synology.SynologyRepositoryImpl
import kotlinx.coroutines.*
import java.util.function.Function


class Request {
    var queryStringParameters: Map<String, String>? = null
}


class Response(private val statusCode: Int, private val headers: Map<String, String>, private val isBase64Encoded: Boolean, private val body: String)


class Handler : Function<Request, Response> {
    private val statusCode = 200
    private val isBase64Encoded = false
    override fun apply(request: Request): Response {
        val headers: MutableMap<String, String> = HashMap()
        headers["Content-Type"] = "text/plain"
        val name = request.queryStringParameters!!["name"]
        return Response(statusCode, headers, isBase64Encoded, String.format("Hello, %s!", name))
    }
}

fun main (): Unit = runBlocking {
    val mattermostRepository = MattermostRepositoryImpl(
            token = MattermostSettings.mattermostToken,
            coroutineScope = this
    )
    val synologyRepository = SynologyRepositoryImpl()
    val botManager = BotManager(mattermost = mattermostRepository, coroutineScope = this, synology = synologyRepository)
    botManager.updatePhoto()
}



