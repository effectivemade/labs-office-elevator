package office.effective.features.booking.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import office.effective.features.booking.converters.BookingRepositoryConverter
import office.effective.features.booking.repository.BookingRepository
import org.koin.core.context.GlobalContext
import java.util.*

fun Route.bookingRouting() {
    route("/bookings") {
        val rep = BookingRepository(GlobalContext.get().get(),
            BookingRepositoryConverter(GlobalContext.get().get(), GlobalContext.get().get()))

        get("{id}") {
            val id: String = call.parameters["id"]
                ?: return@get call.respond(HttpStatusCode.BadRequest)

            call.respond(rep.findById(UUID.fromString(id)).toString())
        }

        get {
            val userId: String = call.request.queryParameters["user_id"]
                ?: return@get call.respond(HttpStatusCode.BadRequest)

            call.respond(rep.findAllByOwnerId(UUID.fromString(userId)).map { it.toString() })
        }
    }
}