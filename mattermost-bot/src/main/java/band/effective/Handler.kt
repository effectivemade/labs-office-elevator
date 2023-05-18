package band.effective

import band.effective.mattermost.MattermostRepositoryImpl
import band.effective.synology.SynologyRepositoryImpl
import kotlinx.coroutines.*
import java.util.function.Function


class Handler : Function<Unit, Unit> {
    // TODO вынести в companion
    private val statusCode = 200
    private val isBase64Encoded = false

    override fun apply(p0: Unit) =
        runBlocking {
            val mattermostRepository = MattermostRepositoryImpl(
                    token = MattermostSettings.mattermostToken,
                    coroutineScope = this
            )
            val synologyRepository = SynologyRepositoryImpl()
            val botManager = BotManager(mattermost = mattermostRepository, coroutineScope = this, synology = synologyRepository)
            botManager.updatePhoto()
        }
}
// TODO вынести логику в функцию
fun main (): Unit = runBlocking {
    val mattermostRepository = MattermostRepositoryImpl(
            token = MattermostSettings.mattermostToken,
            coroutineScope = this
    )
    val synologyRepository = SynologyRepositoryImpl()
    val botManager = BotManager(mattermost = mattermostRepository, coroutineScope = this, synology = synologyRepository)
    botManager.updatePhoto()
    // TODO вынести все в перемеенный окуржения
    System.getenv("dfkdfkk")
}


