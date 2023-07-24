package office.effective.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.exception.ValidationException

fun Application.configureExceptionHandling() {
    install(StatusPages) {
        exception<InstanceNotFoundException> { call, cause ->
            call.respondText(text = "403: $cause", status = HttpStatusCode.BadRequest)
        }
        exception<BadRequestException> { call, cause ->
            call.respondText(text = "403: $cause", status = HttpStatusCode.BadRequest)
        }
        exception<ValidationException> { call, cause ->
            call.respondText(text = "403: $cause", status = HttpStatusCode.BadRequest)
        }
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
}