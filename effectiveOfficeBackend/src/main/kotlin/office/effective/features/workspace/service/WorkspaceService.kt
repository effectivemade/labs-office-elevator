package office.effective.features.workspace.service

import office.effective.features.workspace.repository.WorkspaceRepository
import office.effective.model.Workspace
import office.effective.model.WorkspaceZone
import java.util.UUID

class WorkspaceService(private val repository: WorkspaceRepository) {

    /**
     * Retrieves a workspace model by its id
     *
     * @author Daniil Zavyalov
     */
    fun findById(id: UUID): Workspace? {
        return repository.findById(id)
    }

    /**
     * Returns all workspaces with the given tag
     *
     * @author Daniil Zavyalov
     */
    fun findAllByTag(tag: String): List<Workspace> {
        return repository.findAllByTag(tag)
    }

    /**
     * Returns all workspace zones
     *
     * @author Daniil Zavyalov
     */
    fun findAllZones(): List<WorkspaceZone> {
        return repository.findAllZones()
    }
}