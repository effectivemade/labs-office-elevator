package band.effective.office.tv.network.leader

import band.effective.office.tv.network.leader.models.EventInfo.EventInfoResponse
import band.effective.office.tv.network.leader.models.SearchEvent.SearchEventsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LeaderApi {
    @GET("api/v4/events/search")
    fun searchEvents(@Query("paginationSize") count: Int, @Query("cityId") cityId: Int, @Query("placeIds[]") placeId: Int): Call<SearchEventsResponse>
    @GET("api/v4/events/{eventId}")
    fun eventInfo(@Path("eventId") eventId: Int): Call<EventInfoResponse>
}