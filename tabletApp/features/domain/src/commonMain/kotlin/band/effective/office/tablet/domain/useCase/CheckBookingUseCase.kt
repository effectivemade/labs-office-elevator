package band.effective.office.tablet.domain.useCase

import band.effective.office.network.model.Either
import band.effective.office.tablet.domain.model.ErrorWithData
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import java.util.Calendar

/**Use case for checking booking room opportunity*/
class CheckBookingUseCase(
    private val roomInfoUseCase: RoomInfoUseCase,
    private val checkSettingsUseCase: CheckSettingsUseCase) {
    /**
     * @return Event busy with room booking, if room free, return null*/
    suspend operator fun invoke(event: EventInfo) =
        when (val eventList = eventList()) {
            is Either.Error -> Either.Error(ErrorWithData(
                error = eventList.error.error,
                saveData = eventList.error.saveData?.getBusy(event)
            ))
            is Either.Success -> Either.Success(eventList.data.getBusy(event))
        }


    /**
     * @return All events in current room*/
    private suspend fun eventList(): Either<ErrorWithData<List<EventInfo>>, List<EventInfo>> =
        when (val response = roomInfoUseCase(checkSettingsUseCase())) {
            is Either.Error -> Either.Error(ErrorWithData(response.error.error, response.error.saveData?.getAllEvents()))
            is Either.Success -> {
                Either.Success(
                    response.data.getAllEvents()
                )
            }
        }


    /**
     * @return True, if the moment belongs to the time interval between event start and end*/
    private fun Calendar.belong(event: EventInfo) =
        this >= event.startTime && this <= event.finishTime

    private fun RoomInfo.getAllEvents(): List<EventInfo> =
        if (currentEvent != null) eventList + currentEvent!! else eventList

    private fun List<EventInfo>.getBusy(event: EventInfo): EventInfo? =
        firstOrNull { it.startTime.belong(event) || event.startTime.belong(it) }?.copy()
}