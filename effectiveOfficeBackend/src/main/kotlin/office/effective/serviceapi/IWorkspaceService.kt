package office.effective.serviceapi

import office.effective.model.Workspace
import office.effective.model.WorkspaceZone
import java.time.Instant
import java.util.*

interface IWorkspaceService {

    /**
     * Retrieves a workspace model by its id
     *
     * @author Daniil Zavyalov
     */
    fun findById(id: UUID): Workspace?

    /**
     * Returns all workspaces with the given tag
     *
     * @author Daniil Zavyalov
     */
    fun findAllByTag(tag: String): List<Workspace>

    /**
     * Returns all workspaces with the given tag which are free during the given period
     *
     * @author Daniil Zavyalov
     */
    fun findAllFreeByPeriod(tag: String, beginTimestamp: Instant, endTimestamp: Instant): List<Workspace>

    /**
     * Returns all workspace zones
     *
     * @author Daniil Zavyalov
     */
    fun findAllZones(): List<WorkspaceZone>
}