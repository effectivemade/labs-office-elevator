package band.effective.office.tv.screen.eventStory.models

import band.effective.office.tv.domain.model.message.BotMessage
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

data class AnnualAnniversaryUI(
    private val employeeName: String,
    private val employeePhotoUrl: String,
    val yearsInCompany: Int
) : EmployeeInfoUI(employeeName, employeePhotoUrl, EventType.AnnualAnniversary)

data class MonthAnniversaryUI(
    private val employeeName: String,
    private val employeePhotoUrl: String,
    val yearsInCompany: Int,
    val monthsInCompany: Int
) : EmployeeInfoUI(employeeName, employeePhotoUrl, EventType.MonthAnniversary)


data class NewEmployeeUI(
    private val employeeName: String,
    private val employeePhotoUrl: String,
) : EmployeeInfoUI(employeeName, employeePhotoUrl, EventType.NewEmployee)

data class DuolingoUserInfo(
    val keySort: KeySortDuolingoUser,
    val users: List<DuolingoUserUI>
) : StoryModel(StoryType.Duolingo)

data class MessageInfo(
    val message: BotMessage
) : StoryModel(StoryType.Message)

data class NewYearCounter(val day: Int, val hour: Int, val min: Int, val sec: Int, val mil: Int): StoryModel(StoryType.NewYear)

enum class StoryType {
    Duolingo, Employee, Message, NewYear
}

abstract class StoryModel(val storyType: StoryType)
