package band.effective.office.tv.domain.use_cases

import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.domain.model.duolingo.DuolingoUser
import band.effective.office.tv.domain.model.notion.EmployeeInfoEntity
import band.effective.office.tv.domain.model.notion.processEmployeeInfo
import band.effective.office.tv.repository.duolingo.DuolingoRepository
import band.effective.office.tv.repository.notion.EmployeeInfoRepository
import band.effective.office.tv.screen.duolingo.model.toUI
import band.effective.office.tv.screen.eventStory.KeySortDuolingoUser
import band.effective.office.tv.screen.eventStory.models.DuolingoUserInfo
import band.effective.office.tv.screen.eventStory.models.StoryModel
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

// this use case combine data from many data sources for EvenStory screen
class EventStoryDataCombinerUseCase @Inject constructor(
    private val employeeInfoRepository: EmployeeInfoRepository,
    private val duolingoRepository: DuolingoRepository
) {
    private val countShowUsers = 10

    suspend fun getAllDataForStories() =
        employeeInfoRepository.latestEvents()
            .combine(duolingoRepository.getUsers()) { employeeInfoEntities: Either<String, List<EmployeeInfoEntity>>, usersDuolingo: Either<String, List<DuolingoUser>> ->
                var error = ""
                val duolingoInfo = when (usersDuolingo) {
                    is Either.Success -> {
                        setDuolingoDataForScreens(usersDuolingo.data)
                    }
                    is Either.Failure -> {
                        error = usersDuolingo.error
                        emptyList()
                    }
                }
                val notionInfo = when (employeeInfoEntities) {
                    is Either.Success -> {
                        employeeInfoEntities.data.processEmployeeInfo()
                    }
                    is Either.Failure -> {
                        error = employeeInfoEntities.error
                        emptyList()
                    }
                }
                if (notionInfo.isEmpty() && duolingoInfo.isEmpty()) return@combine Either.Failure(
                    error
                )
                else Either.Success(notionInfo + duolingoInfo)
            }

    private fun List<DuolingoUser>.subListForScreen() =
        subList(
            fromIndex = 0,
            toIndex = if (size <= countShowUsers) size else countShowUsers + 1
        )
            .toUI()

    private fun setDuolingoDataForScreens(duolingoUsers: List<DuolingoUser>) =
        run {
            val userXpSort = DuolingoUserInfo(
                users = duolingoUsers
                    .sortedByDescending { it.totalXp }
                    .subListForScreen(),
                keySort = KeySortDuolingoUser.Xp
            ) as StoryModel
            val userStreakSort = DuolingoUserInfo(
                users = duolingoUsers
                    .sortedByDescending { it.streakDay }
                    .subListForScreen()
                    .filter { it.streakDay != 0 },
                keySort = KeySortDuolingoUser.Streak
            ) as StoryModel
            listOf(userXpSort, userStreakSort)
        }
}