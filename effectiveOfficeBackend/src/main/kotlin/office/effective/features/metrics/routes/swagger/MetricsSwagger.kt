package office.effective.features.metrics.routes.swagger;

import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.ktor.http.*
import office.effective.common.constants.BookingConstants
import office.effective.common.swagger.SwaggerDocument
import java.time.Instant
import kotlin.Unit;

fun SwaggerDocument.percentOfFreeWorkspaces(): OpenApiRoute.() -> Unit = {
    description = "Какой процент переговорок(и рабочих мест) был свободен весь указанный промежуток времени.\n" +
            "     * На коротких промежутках времени подходит как датчик занятости офиса в на момент середины отрезка"


    request {
        queryParameter<Long>("range_from") {
            description = "Lower bound. Should be lover than range_to."

            example = 1692927200000
            required = true
            allowEmptyValue = false

        }
        queryParameter<Long>("range_to") {
            description = "Upper bound. Should be greater than range_from."
            example = 1697027200000
            required = true
            allowEmptyValue = false
        }
    }

    response {
        HttpStatusCode.OK to {
            description = "Returns Map<String, Double>, with content (from 0 to 1) of free regular and meeting workspaces was free all the time you specified."
            body<Map<String, Double>> {
                val res: MutableMap<String, Double> = mutableMapOf("regular" to 0.25, "meeting" to 0.99);
            }
        }
    }
}

fun SwaggerDocument.pecentOfFreeTime(): OpenApiRoute.() -> Unit = {
    description = "Какой долю от всего количества миллисекунд в промежутке времени, пока офис был открыт, была свободна каждая из переговорок"


    request {
        queryParameter<Long>("range_from") {
            description = "Lower bound. Should be lover than range_to. "

            example = 1692927200000
            required = true
            allowEmptyValue = false

        }
        queryParameter<Long>("range_to") {
            description = "Upper bound. Should be greater than range_from."
            example = 1697028200000
            required = true
            allowEmptyValue = false
        }

        queryParameter<Long>("day_starts") {
            description = "Lower bound. Hour in the day. " +
                    "Default value: ${office.effective.common.constants.BookingConstants.MIN_SEARCH_START_TIME} " +
                    "(${java.time.Instant.ofEpochMilli(office.effective.common.constants.BookingConstants.MIN_SEARCH_START_TIME)}). "

            example = 8
            required = false
            allowEmptyValue = false

        }
        queryParameter<Long>("day_ends") {
            description = "Upper bound. Hour in the day."
            example = 20
            required = false
            allowEmptyValue = false
        }
    }

    response {
        HttpStatusCode.OK to {
            description = "Returns Map<String, Double>, with content (from 0 to 1) of free each workspaces was free all the time you specified."
            body<Map<String, Double>> {
                val res: MutableMap<String, Double> = mutableMapOf("regular" to 0.25, "meeting" to 0.99);
            }
        }
    }
}
