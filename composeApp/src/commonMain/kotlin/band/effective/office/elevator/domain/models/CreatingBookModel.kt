package band.effective.office.elevator.domain.models

import kotlinx.datetime.LocalDateTime

data class CreatingBookModel(
    val workSpaceId: String,
    val dateOfStart: LocalDateTime,
    val dateOfEnd: LocalDateTime
)
