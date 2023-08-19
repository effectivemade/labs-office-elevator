package office.effective.features.calendar.repository

import office.effective.common.exception.InstanceNotFoundException
import office.effective.features.workspace.converters.WorkspaceRepositoryConverter
import office.effective.features.workspace.repository.WorkspaceEntity
import office.effective.features.workspace.repository.WorkspaceRepository
import office.effective.model.Workspace
import org.koin.core.context.GlobalContext
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.find
import java.util.UUID

class CalendarRepository(
    private val db: Database,
    private val converter: WorkspaceRepositoryConverter,
    private val workspaceRepository: WorkspaceRepository
) {
    /**
     * @return String - id of calendar for specified workspace
     *
     * @author Danil Kiselev
     * */
    fun findByWorkspace(workspaceId: UUID): String {
        return db.calendarIds.find { it.workspaceId eq workspaceId }?.calendarId ?: throw InstanceNotFoundException(
            CalendarIdEntity::class, "Cannot found calendar id to workspace id $workspaceId", null
        )
    }

    /**
     * @return Workspace model by calendar id (String)
     *
     * @author Danil Kiselev
     * */
    fun findWorkspaceById(calendarId: String): Workspace {
        val workspaceEntity =
            db.calendarIds.find { it.calendarId eq calendarId }?.workspace ?: throw InstanceNotFoundException(
                WorkspaceEntity::class, "Workspace with such google calendar id not found", null
            )
        return converter.entityToModel(
            workspaceEntity, workspaceRepository.findUtilitiesByWorkspaceId(workspaceEntity.id)
        )
    }
}