package office.effective.features.workspace.facade

import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.utils.DatabaseTransactionManager
import office.effective.common.utils.UuidValidator
import office.effective.features.workspace.converters.WorkspaceFacadeConverter
import office.effective.features.workspace.dto.WorkspaceDTO
import office.effective.features.workspace.service.WorkspaceService
import office.effective.model.Workspace

class WorkspaceFacade(private val service: WorkspaceService,
                      private val converter: WorkspaceFacadeConverter,
                      private val transactionManager: DatabaseTransactionManager,
                      private val uuidValidator: UuidValidator) {

    /**
     * Retrieves a workspace model by its id
     *
     * Throws InstanceNotFoundException if workspace with the given id doesn't exist in database
     *
     * @author Daniil Zavyalov
     */
    fun findById(id: String): WorkspaceDTO {
        val uuid = uuidValidator.uuidFromString(id)

        val workspaceDTO: WorkspaceDTO = transactionManager.useTransaction({
            val workspace = service.findById(uuid)
                ?: throw InstanceNotFoundException(Workspace::class, "Workspace with id $id not found", uuid)
            workspace.let { converter.modelToDto(it) }
        })

        return workspaceDTO
    }

    /**
     * Returns all workspaces with the given tag
     *
     * @author Daniil Zavyalov
     */
    fun findAllByTag(tag: String): List<WorkspaceDTO> {
        val result = transactionManager.useTransaction({
            val workspaceList: List<Workspace> = service.findAllByTag(tag)
            workspaceList.map { converter.modelToDto(it) }
        })
        return result
    }
}