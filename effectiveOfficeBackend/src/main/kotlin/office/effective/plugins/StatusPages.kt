package office.effective.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import office.effective.common.exception.UtilityNotFoundException
import office.effective.common.exception.WorkspaceNotFoundException
import office.effective.common.exception.WorkspaceTagNotFoundException

fun Application.configureExceptionHandling() {
    install(StatusPages) {
        exception<WorkspaceNotFoundException> { call, cause ->
            call.respondText(text = "403: $cause", status = HttpStatusCode.BadRequest)
        }
        exception<WorkspaceTagNotFoundException> { call, cause ->
            call.respondText(text = "403: $cause", status = HttpStatusCode.BadRequest)
        }
        exception<UtilityNotFoundException> { call, cause ->
            call.respondText(text = "403: $cause", status = HttpStatusCode.BadRequest)
        }
        exception<BadRequestException> { call, cause ->
            call.respondText(text = "403: $cause", status = HttpStatusCode.BadRequest)
        }
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
}