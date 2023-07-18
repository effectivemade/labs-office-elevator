package band.effective.office.tablet.network.repository

interface OrganizerRepository {
    suspend fun getOrganizersList(): List<String>
}