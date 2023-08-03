package office.effective.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.exception.MissingIdException
import office.effective.common.exception.ValidationException

fun Application.configureExceptionHandling() {
    install(StatusPages) {
        exception<InstanceNotFoundException> { call, cause ->
            call.respondText(text = "404: $cause", status = HttpStatusCode.NotFound)
        }
        exception<BadRequestException> { call, cause ->
            call.respondText(text = "400: $cause", status = HttpStatusCode.BadRequest)
        }
        exception<ValidationException> { call, cause ->
            call.respondText(text = "404: $cause", status = HttpStatusCode.NotFound)
        }
        exception<RequestValidationException> { call, cause ->
            call.respondText(text = "400: ${cause.reasons.joinToString()}", status = HttpStatusCode.BadRequest)
        }
        exception<MissingIdException> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
}