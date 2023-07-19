package office.effective.features.workspace.config

import office.effective.features.workspace.converters.WorkspaceRepositoryConverter
import org.koin.dsl.module

val workspaceDiModule = module(createdAtStart = true) {
    single {
        WorkspaceRepositoryConverter()
    }
}
