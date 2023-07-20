package office.effective.features.workspace.config

import office.effective.features.workspace.converters.WorkspaceFacadeConverter
import office.effective.features.workspace.converters.WorkspaceRepositoryConverter
import office.effective.features.workspace.facade.WorkspaceFacade
import office.effective.features.workspace.repository.WorkspaceRepository
import office.effective.features.workspace.service.WorkspaceService
import org.koin.dsl.module

val workspaceDiModule = module(createdAtStart = true) {
    single { WorkspaceRepositoryConverter() }
    single { WorkspaceRepository() }
    single { WorkspaceService() }
    single { WorkspaceFacadeConverter() }
    single { WorkspaceFacade() }
}
