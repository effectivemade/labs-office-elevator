package band.effective.office.tvServer.route.mattermost

import band.effective.office.tvServer.service.mattermost.MattermostService
import band.effective.office.tvServer.utils.savePipeline
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.mattermost() {
    val mattermostService: MattermostService by inject()
    post("/outgoing") {
        savePipeline {
            val dto = call.receive<WebHookDto>()
            mattermostService.handelMessage(dto.post_id, dto.channel_id)
            call.respond(HttpStatusCode.OK)
        }
    }
}