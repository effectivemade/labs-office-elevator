package band.effective.office.tablet.domain

import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.network.RoomInfoRepository

class RoomInteractorImpl(private val roomInfoRepository: RoomInfoRepository): RoomInteractor {
    override fun getRoomInfo(name: String): RoomInfo = roomInfoRepository.getRoomInfo(name)
}