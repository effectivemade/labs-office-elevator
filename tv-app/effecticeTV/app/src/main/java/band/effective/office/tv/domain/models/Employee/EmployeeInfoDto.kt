package band.effective.office.tv.domain.models.Employee

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class EmployeeInfoDto(
    @Json(name = "Name") val firstName: String?,
    @Json(name = "Start Date") val startDate: String?,
    @Json(name = "Next B-DAY") val nextBirthdayDate: String?,
    val photoUrl: String? = "",
)