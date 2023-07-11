package band.effective.office.tablet.network.repository.impl

import band.effective.office.tablet.network.api.Api
import band.effective.office.tablet.network.repository.CancelRepository

class CancelRepositoryImpl(private val api: Api) : CancelRepository {
    override suspend fun cancelEvent(): Boolean = api.cancelEvent()
}