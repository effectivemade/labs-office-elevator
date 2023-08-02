package office.effective.features.booking.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import office.effective.features.booking.converters.BookingRepositoryConverter
import office.effective.features.booking.repository.BookingRepository
import office.effective.features.user.repository.UserRepository
import office.effective.features.workspace.repository.WorkspaceRepository
import office.effective.model.Booking
import org.koin.core.context.GlobalContext
import java.time.Instant
import java.util.*

fun Route.bookingRouting() {
    route("/bookings") {
        val rep = BookingRepository(GlobalContext.get().get(),
            BookingRepositoryConverter(GlobalContext.get().get(), GlobalContext.get().get(), GlobalContext.get().get()))
        val userRep = UserRepository(GlobalContext.get().get(), GlobalContext.get().get())
        val workspaceRep = WorkspaceRepository(GlobalContext.get().get(), GlobalContext.get().get())

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
        post {
            val booking = Booking(
                userRep.findById(UUID.fromString("87e66ee0-2550-4188-8d79-75560125836a")),
                userRep.findByTag(UUID.fromString("6cccb7ae-3258-4bd1-b486-c9b74f740da8")).toList(),
                workspaceRep.findById(UUID.fromString("3d951692-dbe1-4102-9b7d-d1881cff8d17"))!!,
                null,
                Instant.now(),
                Instant.now()
            )
            call.response.status(HttpStatusCode.Created)
            call.respond(rep.save(booking).toString())
        }
        put {
            val booking = Booking(
                userRep.findById(UUID.fromString("87e66ee0-2550-4188-8d79-75560125836a")),
                userRep.findByTag(UUID.fromString("b59ecd48-368a-4cb6-b07e-de4376ddf6a6")).toList(),
                workspaceRep.findById(UUID.fromString("3d951692-dbe1-4102-9b7d-d1881cff8d17"))!!,
                UUID.fromString("c63d5296-fd8b-4b4c-ba75-0e9285b3c349"),
                Instant.now(),
                Instant.now()
            )
            call.respond(rep.update(booking).toString())
        }
        delete("{id}") {
            val id: String = call.parameters["id"]
                ?: return@delete call.respond(HttpStatusCode.BadRequest)
            rep.deleteById(UUID.fromString(id))
            call.respond(HttpStatusCode.NoContent)
        }
    }
}