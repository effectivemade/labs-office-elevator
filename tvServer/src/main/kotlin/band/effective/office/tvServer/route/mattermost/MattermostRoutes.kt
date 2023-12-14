package band.effective.office.tvServer.route.mattermost

import band.effective.office.tvServer.model.SavedMessage
import band.effective.office.tvServer.service.mattermost.MattermostService
import band.effective.office.tvServer.utils.savePipeline
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun Route.mattermost() {
    val mattermostService: MattermostService by inject()
    post("/outgoing") {
        savePipeline {
            val dto = call.receive<WebHookDto>()
            mattermostService.handelMessage(dto.post_id, dto.channel_id)
            call.respond(HttpStatusCode.OK)
        }
    }
    get("/message") {
        savePipeline {
            call.respond(mattermostService.getPosts().map { it.toDto() })
        }
    }
    post("/message") {
        savePipeline {
            mattermostService.savePost(call.receive<MessageDto>().toMessage())
            call.respond(HttpStatusCode.Created)
        }
    }
    put("/message") {
        savePipeline {
            mattermostService.updatePost(call.receive<MessageDto>().toMessage())
            call.respond(HttpStatusCode.OK)
        }
    }
    delete("/message/{id}") {
        savePipeline {
            mattermostService.deletePost(call.parameters["id"]!!)
            call.respond(HttpStatusCode.OK)
        }
    }
}

private fun SavedMessage.toDto(): MessageDto = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm").let { formatter ->
    MessageDto(
        message = message,
        start = start.format(formatter),
        finish = finish.format(formatter)
    )
}

private fun MessageDto.toMessage(): SavedMessage = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm").let { formatter ->
    SavedMessage(
        message = message,
        start = LocalDateTime.parse(start, formatter),
        finish = LocalDateTime.parse(finish, formatter)
    )
}
