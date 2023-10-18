package office.effective.features.logsAndMetriks

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.micrometer.prometheus.PrometheusMeterRegistry
import org.koin.core.context.GlobalContext

fun Route.metrics(){
    get("/metrics") {
        val appMicrometerRegistry: PrometheusMeterRegistry = GlobalContext.get().get()
        call.respond(appMicrometerRegistry.scrape())
    }
}