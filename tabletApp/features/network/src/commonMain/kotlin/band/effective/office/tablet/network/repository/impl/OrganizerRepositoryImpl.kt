package band.effective.office.tablet.network.repository.impl

import band.effective.office.tablet.network.api.Api
import band.effective.office.tablet.network.repository.OrganizerRepository

class OrganizerRepositoryImpl(private val api: Api) : OrganizerRepository {
    override suspend fun getOrganizersList(): List<String> = api.getOrganizers()
}