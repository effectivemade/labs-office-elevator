package band.effective.office.elevator.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import band.effective.office.elevator.ui.bottomSheets.sbp.SBPSheet
import band.effective.office.elevator.ui.bottomSheets.sbp.model.SBPBankInfo

@Preview
@Composable
private fun SBPPreview(){
    SBPSheet(
        banks = listOf(
            SBPBankInfo(
                bankName = "Тинькофф Банк",
                logo = "https://qr.nspk.ru/proxyapp/logo/bank100000000004.png",
                schema = "bank100000000004",
                packageName = "com.idamob.tinkoff.android"
            ),
            SBPBankInfo(
                bankName = "Тинькофф Банк",
                logo = "https://qr.nspk.ru/proxyapp/logo/bank100000000004.png",
                schema = "bank100000000004",
                packageName = "com.idamob.tinkoff.android"
            ),
            SBPBankInfo(
                bankName = "Тинькофф Банк",
                logo = "https://qr.nspk.ru/proxyapp/logo/bank100000000004.png",
                schema = "bank100000000004",
                packageName = "com.idamob.tinkoff.android"
            ),SBPBankInfo(
                bankName = "Тинькофф Банк",
                logo = "https://qr.nspk.ru/proxyapp/logo/bank100000000004.png",
                schema = "bank100000000004",
                packageName = "com.idamob.tinkoff.android"
            ),SBPBankInfo(
                bankName = "Тинькофф Банк",
                logo = "https://qr.nspk.ru/proxyapp/logo/bank100000000004.png",
                schema = "bank100000000004",
                packageName = "com.idamob.tinkoff.android"
            ),
        ),
        query = "",
        onQueryUpdate = {},
        onClickBank = {},
        onClickBack = {}
    )
}


