package office.effective.features.workspace.config

import office.effective.features.workspace.converters.WorkspaceConverter
import org.koin.dsl.module

val workspaceDiModule = module(createdAtStart = true) {
    single {
        WorkspaceConverter()
    }
}
