package office.effective.plugins

import io.ktor.server.application.*
import office.effective.common.utils.commonDiModule
import org.koin.ktor.plugin.Koin

fun Application.configureDI() {
    install(Koin) {
        modules(commonDiModule)
    }
}
