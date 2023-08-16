package office.effective.features.calendar.repository

import office.effective.common.exception.InstanceNotFoundException
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.find
import java.util.UUID

class CalendarRepository(private val db: Database) {
    fun findByWorkspace(workspaceId: UUID): String {
        return db.calendarIds.find { it.workspaceId eq workspaceId }?.calendarId ?: throw InstanceNotFoundException(
            CalendarIdEntity::class, "Cannot found calendar id to workspace id $workspaceId", null
        )
    }
}