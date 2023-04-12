package band.effective.office.tv.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Text
import band.effective.office.tv.R
import band.effective.office.tv.domain.models.Employee.Anniversary
import band.effective.office.tv.domain.models.Employee.EmployeeInfo
import band.effective.office.tv.domain.models.Employee.EventType

@Composable
fun EventInfo(employeeInfoes: List<EmployeeInfo>) {
    //Simple UI to demosntrate logic
    val isBirthday = employeeInfoes[0].eventType == EventType.Birthday
    val isAnniversary = employeeInfoes[0].eventType == EventType.Anniversary
    val isNewEmployee = employeeInfoes[0].eventType == EventType.NewEmployee
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(text = employeeInfoes[0].name + ",")
        when {
            isBirthday -> Text(text = stringResource(id = R.string.congratulations_birthday))
            isAnniversary -> {
                val event: Anniversary = employeeInfoes[0] as Anniversary
                Text(
                    text = stringResource(id = R.string.with_us) + event.yearsInCompany + stringResource(
                        id = R.string.for_years
                    )
                )
            }
            isNewEmployee -> Text(text = stringResource(id = R.string.now_in_team))
        }
    }
}