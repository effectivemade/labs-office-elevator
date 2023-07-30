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
    route("/booking") {
        val rep = BookingRepository(GlobalContext.get().get(),
            BookingRepositoryConverter(GlobalContext.get().get(), GlobalContext.get().get()))

        get("{id}") {
            val id: String = call.parameters["id"]
                ?: return@get call.respond(HttpStatusCode.BadRequest)

            call.respond(rep.findById(UUID.fromString(id)).toString())
        }
    }
}