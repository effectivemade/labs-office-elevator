package band.effective.office.tv.repository.workTogether

import java.util.Calendar

data class Teammate(
    val id: String,
    val name: String,
    val positions: List<String>,
    val employment: String,
    val startDate: Calendar,
    val duolingo: String?
)
