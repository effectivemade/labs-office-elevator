package office.effective.features.workspace.repository

import office.effective.features.workspace.converters.WorkspaceConverter
import office.effective.model.Workspace
import org.koin.java.KoinJavaComponent.inject
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import java.util.UUID

class WorkspaceRepository {
    private val database by inject<Database>(clazz = Database::class.java)
    private val converter by inject<WorkspaceConverter>(clazz = WorkspaceConverter::class.java)

    fun findById(workspaceId: UUID): Workspace? {
        val entity: WorkspaceEntity? = database.workspaces.find { it.id eq workspaceId }
        return entity?.let { converter.EntityToModel(it) }
    }
}