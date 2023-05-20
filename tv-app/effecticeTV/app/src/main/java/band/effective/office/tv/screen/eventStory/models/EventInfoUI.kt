package band.effective.office.tv.screen.eventStory.models

import band.effective.office.tv.domain.model.duolingo.DuolingoUser
import band.effective.office.tv.domain.model.notion.EventType
import band.effective.office.tv.screen.duolingo.model.DuolingoUserUI
import band.effective.office.tv.screen.eventStory.KeySortDuolingoUser

sealed class EmployeeInfoUI(
    val name: String,
    val photoUrl: String,
    val eventType: EventType
    ) : StoryModel(StoryType.Employee)

data class BirthdayUI(
    val employeeName: String,
    val employeePhotoUrl: String,
) : EmployeeInfoUI(employeeName, employeePhotoUrl, EventType.Birthday)

data class AnniversaryUI(
    private val employeeName: String,
    private val employeePhotoUrl: String,
    val yearsInCompany: Int
) : EmployeeInfoUI(employeeName, employeePhotoUrl, EventType.Anniversary)

data class NewEmployeeUI(
    private val employeeName: String,
    private val employeePhotoUrl: String,
) : EmployeeInfoUI(employeeName, employeePhotoUrl, EventType.NewEmployee)

data class DuolingoUserInfo(
    val keySort: KeySortDuolingoUser,
    val users: List<DuolingoUserUI>
): StoryModel(StoryType.Duolingo)

enum class StoryType {
    Duolingo, Employee
}

abstract class StoryModel(val storyType: StoryType)
