package office.effective.features.booking.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import office.effective.features.booking.converters.BookingRepositoryConverter
import office.effective.features.booking.dto.BookingDTO
import office.effective.features.booking.facade.BookingFacade
import office.effective.features.booking.repository.BookingRepository
import office.effective.features.user.repository.UserRepository
import office.effective.features.workspace.repository.WorkspaceRepository
import office.effective.model.Booking
import org.koin.core.context.GlobalContext
import java.time.Instant
import java.util.*

fun Route.bookingRouting() {
    route("/bookings") {
        val bookingFacade = BookingFacade(
            GlobalContext.get().get(),
            GlobalContext.get().get(),
            GlobalContext.get().get(),
            GlobalContext.get().get()
        )

        get("{id}") {
            val id: String = call.parameters["id"]
                ?: return@get call.respond(HttpStatusCode.BadRequest)

            call.respond(bookingFacade.findById(id))
        }

        get {
            val userId: String = call.request.queryParameters["user_id"]
                ?: return@get call.respond(HttpStatusCode.BadRequest)

            call.respond(bookingFacade.findAllByOwnerId(userId))
        }
        post {
            val dto = call.receive<BookingDTO>()

            call.response.status(HttpStatusCode.Created)
            call.respond(bookingFacade.post(dto))
        }
        put {
            val dto = call.receive<BookingDTO>()

            call.respond(bookingFacade.put(dto))
        }
        delete("{id}") {
            val id: String = call.parameters["id"]
                ?: return@delete call.respond(HttpStatusCode.BadRequest)
            bookingFacade.deleteById(id)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}