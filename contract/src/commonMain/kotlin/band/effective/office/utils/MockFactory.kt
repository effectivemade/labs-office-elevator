package band.effective.office.utils

import band.effective.office.network.dto.BookingDTO
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

    private fun lanUtility(value: Int) = UtilityDTO(
        count = value,
        iconUrl = "",
        id = "",
        name = "lan"
    )

    private fun placeUtility(value: Int) = UtilityDTO(
        count = value,
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

    private fun user(name: String, role: String): UserDTO = TODO()

    private fun booking(
        owner: UserDTO,
        start: Pair<Int, Int>,
        finish: Pair<Int, Int>,
        workspace: WorkspaceDTO
    ) =
        BookingDTO(
            id = "${Random.nextInt(10000)}",
            beginBooking = getTime(start.first, start.second),
            endBooking = getTime(finish.first, finish.second),
            owner = owner,
            participants = listOf(),
            workspace = workspace
        )

    fun workspaces() = listOf<WorkspaceDTO>()

    fun meetingRooms() = listOf(
        WorkspaceDTO(
            id = "Sirius",
            name = "Sirius",
            utilities = listOf(lanUtility(0), placeUtility(1)),
            tag = "meeting"
        ),
        WorkspaceDTO(
            id = "Pluto",
            name = "Pluto",
            utilities = listOf(lanUtility(1), placeUtility(3)),
            tag = "meeting"
        ),
        WorkspaceDTO(
            id = "Moon",
            name = "Moon",
            utilities = listOf(lanUtility(5), placeUtility(8)),
            tag = "meeting"
        ),
        WorkspaceDTO(
            id = "Antares",
            name = "Antares",
            utilities = listOf(lanUtility(10), placeUtility(10)),
            tag = "meeting"
        ),
        WorkspaceDTO(
            id = "Sun",
            name = "Sun",
            utilities = listOf(lanUtility(8), placeUtility(10), tvUtility()),
            tag = "meeting"
        )
    )

    private val names = listOf("Ольга Белозерова", "Матвей Авгуль", "Лилия Акентьева")

    fun users() = names.map { user(it, "ADMIN") }

    fun bookings(workspaceDTO: WorkspaceDTO, owner: List<UserDTO>) =
        owner.mapIndexed { index, name ->
            booking(
                owner = name,
                start = Pair(11 + index, (index % 2) * 30),
                finish = Pair(12 + index, (index % 2) * 30),
                workspace = workspaceDTO
            )
        }

    fun success() = SuccessResponse(status = "ok")
}