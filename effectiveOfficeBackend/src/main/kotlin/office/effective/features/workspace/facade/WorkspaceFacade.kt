package office.effective.features.workspace.facade

import io.ktor.server.plugins.*
import office.effective.common.exception.WorkspaceNotFoundException
import office.effective.features.workspace.converters.WorkspaceFacadeConverter
import office.effective.features.workspace.dto.WorkspaceDTO
import office.effective.features.workspace.service.WorkspaceService
import office.effective.model.Workspace
import org.koin.core.context.GlobalContext
import java.lang.IllegalArgumentException
import java.util.UUID

class WorkspaceFacade {
    private val service: WorkspaceService = GlobalContext.get().get()
    private val converter: WorkspaceFacadeConverter = GlobalContext.get().get()

    fun findById(id: String): WorkspaceDTO {
        val uuid: UUID
        try {
            uuid = UUID.fromString(id)
        } catch (ex: IllegalArgumentException) {
            throw BadRequestException("Provided id is not UUID: " + ex.message)
        }
        val workspace: Workspace = service.findById(uuid)
            ?: throw WorkspaceNotFoundException("Workspace with id $id not found")
        return converter.workspaceModelToDto(workspace)
    }

    fun findAllByTag(tag: String): List<WorkspaceDTO> {
        return service.findAllByTag(tag).map {
            converter.workspaceModelToDto(it)
        }
    }
}