package office.effective.features.workspace.service

import office.effective.features.workspace.repository.WorkspaceRepository
import office.effective.model.Workspace
import java.util.UUID

class WorkspaceService(private val repository: WorkspaceRepository) {

    fun findById(id: UUID): Workspace? {
        return repository.findById(id)
    }

    fun findAllByTag(tag: String): List<Workspace> {
        return repository.findAllByTag(tag)
    }
}