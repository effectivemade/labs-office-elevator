package band.effective.office.tablet.domain

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.network.RoomInfoRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Calendar
import java.util.GregorianCalendar

class RoomInteractorImpl(private val roomInfoRepository: RoomInfoRepository) : RoomInteractor,
    KoinComponent {
    private val mockController: MockController by inject()
    override fun getRoomInfo(name: String): RoomInfo = roomInfoRepository.getRoomInfo(name)
    override fun getOrganizers(): List<String> = roomInfoRepository.getOrganizers()
    override fun checkRoom(name: String, dateTime: Calendar): EventInfo? =
        if (mockController.isBusyTime) EventInfo(
            startTime = GregorianCalendar(),
            finishTime = GregorianCalendar(),
            organizer = "Ольга Белозерова"
        )
        else null
}