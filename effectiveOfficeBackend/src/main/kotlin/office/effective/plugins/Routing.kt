package office.effective.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.micrometer.prometheus.PrometheusMeterRegistry
import office.effective.features.user.routes.userRouting
import office.effective.features.booking.routes.bookingRouting
import office.effective.features.notifications.routes.calendarNotificationsRouting
import office.effective.features.workspace.routes.workspaceRouting
import org.koin.core.context.GlobalContext

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        workspaceRouting()
        userRouting()
        bookingRouting()
        calendarNotificationsRouting()
        
        get("/metrics"){
            val appMicrometerRegistry : PrometheusMeterRegistry = GlobalContext.get().get()
            call.respond(appMicrometerRegistry.scrape())
        }
    }

}
