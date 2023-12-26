package band.effective.office.elevator.ui.bottomSheets.sbp.model

import band.effective.office.elevator.data.models.sbp.SBPBank

data class SBPBankInfo(
    val bankName: String,
    val schema: String,
    val packageName: String?,
    val logo: String
)

fun SBPBank.toDomain() =
    SBPBankInfo(
       packageName = packageName,
        bankName = bankName,
        schema = schema,
        logo = logoURL
    )

fun List<SBPBank>.listToDomain() = map { it.toDomain() }