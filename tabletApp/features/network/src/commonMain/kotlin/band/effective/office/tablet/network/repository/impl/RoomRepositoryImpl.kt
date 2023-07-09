package band.effective.office.tablet.network.repository.impl

import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.network.api.Api
import band.effective.office.tablet.network.repository.RoomRepository

class RoomRepositoryImpl(private val api: Api): RoomRepository {
    override suspend fun getRoomInfo() = api.getRoomInfo()
}