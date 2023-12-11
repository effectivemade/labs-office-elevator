package band.effective.office.elevator.ui.bottomSheets.sbp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.SearchTextField
import band.effective.office.elevator.textInBorderGray
import band.effective.office.elevator.ui.bottomSheets.sbp.components.BankCad
import band.effective.office.elevator.ui.bottomSheets.sbp.model.SBPBankInfo
import band.effective.office.elevator.ui.bottomSheets.sbp.store.SBPStore
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun SBPSheet(
    banks: List<SBPBankInfo>,
    query: String,
    onQueryUpdate: (String) -> Unit,
    onClickBank: (SBPBankInfo) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        SearchTextField(
            query = query,
            onQueryUpdate = onQueryUpdate,
            placeholderText = stringResource(MainRes.strings.employee_hint)
        )

        Spacer(modifier = Modifier.height(32.dp))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(banks) { spbBank ->
                BankCad(
                    bankInfo = spbBank, onClick = onClickBank
                )
            }
        }
    }
}