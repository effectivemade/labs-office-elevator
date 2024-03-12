package office.effective.features.metrics.routes

import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import office.effective.features.metrics.service.MetricsService
import org.koin.core.context.GlobalContext
import java.time.Instant

fun Route.metrics() {
    route("metrics") {
        val metricsService: MetricsService = GlobalContext.get().get()
        get("/percentOfFreeWorkspaces") {
            val startTime: Instant = Instant.ofEpochMilli(call.request.queryParameters["range_from"].let {
                it?.toLongOrNull() ?: throw BadRequestException("range_to can't be parsed to Long")
            })
            val endTime: Instant = Instant.ofEpochMilli(call.request.queryParameters["range_to"].let {
                it?.toLongOrNull() ?: throw BadRequestException("range_to can't be parsed to Long")
            })
            call.respond(metricsService.calculateOfficeOccupancy(startTime, endTime))
            return@get
        }

        get("/pecentOfFreeTime") {
            val dayStarts: Int = call.request.queryParameters["day_starts"].let {
                it?.toInt() ?: throw BadRequestException("day_starts can't be parsed to Int")
            }
            val dayEnds: Int =call.request.queryParameters["day_ends"].let {
                it?.toInt() ?: throw BadRequestException("day_ends can't be parsed to Int")
            }
            val startTime: Instant = Instant.ofEpochMilli(call.request.queryParameters["range_from"].let {
                it?.toLongOrNull() ?: throw BadRequestException("range_from can't be parsed to Long")
            })
            val endTime: Instant = Instant.ofEpochMilli(call.request.queryParameters["range_to"].let {
                it?.toLongOrNull() ?: throw BadRequestException("range_to can't be parsed to Long")
            })
            call.respond(metricsService.calcPlaceHours(dayStarts, dayEnds, startTime, endTime))
            return@get
        }
    }


}