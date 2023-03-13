package band.effective.office.tv.network.leader

import band.effective.office.tv.network.leader.models.SearchEventsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LeaderApi {
    @GET("api/v4/events/search")
    fun searchEvents(@Query("paginationSize") count: Int, @Query("placeIds[]") placeId: Int): Call<SearchEventsResponse>
    @GET("api/v4/events/search")
    fun searchEventsByCity(@Query("paginationSize") count: Int, @Query("cityId") cityId: Int): Call<SearchEventsResponse>

}