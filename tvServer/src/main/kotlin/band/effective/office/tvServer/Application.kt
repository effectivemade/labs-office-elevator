package band.effective.office.tvServer

import band.effective.office.tvServer.di.dataModule
import band.effective.office.tvServer.di.domainModule
import band.effective.office.tvServer.di.presentationModule
import band.effective.office.tvServer.repository.leaderId.LeaderIdEventInfo
import band.effective.office.tvServer.service.LeaderIdService
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import java.time.format.DateTimeFormatter

fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1", module = Application::module).start(wait = true)
}

fun Application.module() {
    install(Koin) {
        modules(dataModule, domainModule, presentationModule)
    }
    install(ContentNegotiation) {
        json()
    }
    routing {
        val leaderIdService: LeaderIdService by inject()
        get("/") {
            try {
                call.respond(leaderIdService.getEvents().toList().map { it.toDto() })
            } catch (e: Exception) {
                call.respondText(e.message ?: "error")
            }
        }
    }
}

private fun LeaderIdEventInfo.toDto() = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").let {
    LeaderIdDto(
        id = id,
        name = name,
        startDateTime = startDateTime.format(it),
        finishDateTime = finishDateTime.format(it),
        isOnline = isOnline,
        photoUrl = photoUrl,
        organizer = organizer,
        speakers = speakers,
        endRegDate = endRegDate?.format(it)
    )
}

@Serializable
data class LeaderIdDto(
    val id: Int,
    val name: String,
    val startDateTime: String,
    val finishDateTime: String,
    val isOnline: Boolean,
    val photoUrl: String?,
    val organizer: String?,
    val speakers: List<String>?,
    val endRegDate: String?
)
