package office.effective.plugins

import io.ktor.server.application.*
import office.effective.common.utils.commonDiModule
import office.effective.features.user.di.userDIModule
import office.effective.features.workspace.config.workspaceDiModule
import org.koin.ktor.plugin.Koin

fun Application.configureDI() {
    install(Koin) {
        modules(commonDiModule, userDIModule, workspaceDiModule)
    }
}
