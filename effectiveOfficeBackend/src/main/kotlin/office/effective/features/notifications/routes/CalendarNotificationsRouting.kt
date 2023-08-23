package office.effective.features.notifications.routes

import io.github.smiley4.ktorswaggerui.dsl.route
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import office.effective.common.notifications.INotificationSender
import org.koin.core.context.GlobalContext

fun Route.calendarNotificationsRouting() {
    route("/notifications") {
        val messageSender: INotificationSender = GlobalContext.get().get()

        post() {
            messageSender.sendEmptyMessage("booking")
            call.respond(HttpStatusCode.OK)
        }
    }
}
