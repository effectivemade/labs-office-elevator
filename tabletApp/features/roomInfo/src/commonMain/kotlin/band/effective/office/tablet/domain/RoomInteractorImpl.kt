package band.effective.office.tablet.domain

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.network.RoomInfoRepository
import java.util.Calendar
import java.util.GregorianCalendar

class RoomInteractorImpl(private val roomInfoRepository: RoomInfoRepository) : RoomInteractor {
    override fun getRoomInfo(name: String): RoomInfo = roomInfoRepository.getRoomInfo(name)
    override fun getOrganizers(): List<String> = roomInfoRepository.getOrganizers()
    override fun checkRoom(name: String, dateTime: Calendar): EventInfo? =
        EventInfo(
            startTime = GregorianCalendar(),
            finishTime = GregorianCalendar(),
            organizer = "Ольга Белозерова"
        )
}