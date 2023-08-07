package band.effective.office.utils

import band.effective.office.network.dto.BookingInfo
import band.effective.office.network.dto.SuccessResponse
import band.effective.office.network.dto.UserDTO
import band.effective.office.network.dto.UtilityDTO
import band.effective.office.network.dto.WorkspaceDTO
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.todayIn
import kotlin.random.Random

class MockFactory {
    private fun getTime(hours: Int = 0, minutes: Int = 0) =
        Clock.System.todayIn(TimeZone.currentSystemDefault()).run {
            LocalDateTime(
                year = year,
                monthNumber = monthNumber,
                dayOfMonth = dayOfMonth,
                hour = hours,
                minute = minutes
            ).toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
        }

    private fun lanUtility() = UtilityDTO(
        count = Random.nextInt(0, 20),
        iconUrl = "",
        id = "",
        name = "lan"
    )

    private fun placeUtility() = UtilityDTO(
        count = Random.nextInt(0, 20),
        iconUrl = "",
        id = "",
        name = "place"
    )

    private fun tvUtility() = UtilityDTO(
        count = 1,
        iconUrl = "",
        id = "",
        name = "tv"
    )

    private fun user(name: String, role: String) = UserDTO(
        active = Random.nextBoolean(),
        avatarUrl = "",
        fullName = name,
        id = name,
        integrations = listOf(),
        role = role
    )

    private fun booking(owner: String, start: Pair<Int, Int>, finish: Pair<Int, Int>, workspace: String) =
        BookingInfo(
            id = "${Random.nextInt(10000)}",
            begin = getTime(start.first, start.second),
            end = getTime(finish.first, finish.second),
            ownerId = owner,
            participants = listOf(),
            workspaceId = workspace
        )

    fun workspaces() = listOf<WorkspaceDTO>()

    fun meetingRooms() = listOf(
        WorkspaceDTO(
            id = "Sirius",
            name = "Sirius",
            utilities = listOf(lanUtility(), placeUtility())
        ),
        WorkspaceDTO(
            id = "Pluto",
            name = "Pluto",
            utilities = listOf(lanUtility(), placeUtility())
        ),
        WorkspaceDTO(id = "Moon", name = "Moon", utilities = listOf(lanUtility(), placeUtility())),
        WorkspaceDTO(
            id = "Antares",
            name = "Antares",
            utilities = listOf(lanUtility(), placeUtility())
        ),
        WorkspaceDTO(
            id = "Sun",
            name = "Sun",
            utilities = listOf(lanUtility(), placeUtility(), tvUtility())
        )
    )

    private val names = listOf("Ольга Белозерова", "Матвей Авгуль", "Лилия Акентьева")

    fun users() = names.map { user(it, "ADMIN") }

    fun bookings() = names.mapIndexed { index, name ->
        booking(
            owner = name,
            start = Pair(11 + index, (index % 2) * 30),
            finish = Pair(12 + index, (index % 2) * 30),
            workspace = "Sirius"
        )
    }

    fun success() = SuccessResponse(status = "ok")
}