package band.effective.office.tv.repository.leaderIdRepository.impl

import band.effective.office.tv.model.LeaderIdEventInfo
import band.effective.office.tv.network.core.Either
import band.effective.office.tv.network.leader.LeaderApi
import band.effective.office.tv.network.leader.models.searchEvent.SearchEventsResponse
import band.effective.office.tv.repository.leaderIdRepository.LeaderIdEventsInfoRepository
import java.util.*
import javax.inject.Inject

class LeaderIdEventsInfoRepositoryImpl @Inject constructor(val leaderApi: LeaderApi): LeaderIdEventsInfoRepository {
    override suspend fun getEventsInfo(): List<LeaderIdEventInfo> {
        val eventList:kotlin.collections.List<LeaderIdEventInfo> =
            when (val either = leaderApi.searchEvents(10,893,3942)){
            is Either.Success -> serachEventResponseToListOfLeaderIdPartInfo(either.data)
            is Either.Failure -> listOf(LeaderIdEventInfo.NullInfo(either.error.message))
        }
        return updateEventList(eventList)
    }
    private fun serachEventResponseToListOfLeaderIdPartInfo(searchEventsResponse: SearchEventsResponse): List<LeaderIdEventInfo>{
        val listOfLeaderIdPartInfo = mutableListOf<LeaderIdEventInfo>()
        for (item in searchEventsResponse.data.items){
            listOfLeaderIdPartInfo.add(LeaderIdEventInfo.PartInfo(
                id = item.id,
                name = item.fullName,
                dateTime = stringToDateTime(item.dateStart),
                isOnline = item.format == "online",
                photoUrl = item.photo,
                errorText = "not update"
            ))
        }
        return listOfLeaderIdPartInfo
    }

    private suspend fun updateEventList(list: List<LeaderIdEventInfo>):List<LeaderIdEventInfo> =
        list.map {
            if (it is LeaderIdEventInfo.PartInfo){
                when (val either = leaderApi.eventInfo(it.id)){
                    is Either.Success -> LeaderIdEventInfo.FullInfo(it,either.data.data.organizers[0].name, either.data.data.speakers.map { it.user.firstName })
                    is Either.Failure -> it.copy(errorText = either.error.message)
                }
            }
            else it
        }
    private fun stringToDateTime(str: String): GregorianCalendar{
        val date = str.substring(0,str.indexOf(" "))
        val time = str.replace(date+" ","")
        val dateComponents = date.split("-")
        val timeComponents = time.split(":")
        return GregorianCalendar(dateComponents[0].toInt(),dateComponents[1].toInt(),dateComponents[2].toInt(),timeComponents[0].toInt(),timeComponents[1].toInt())
    }
}

