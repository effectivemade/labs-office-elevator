package band.effective.office.tv.screen.eventStory

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import band.effective.office.tv.R
import band.effective.office.tv.domain.models.Employee.EmployeeInfo

@Composable
fun EventStoryScreen() {

    val context = LocalContext.current
    val errorMessage = stringResource(id = R.string.error_occurred)


    val viewModel = hiltViewModel<EventStoryViewModel>()
    val state by viewModel.uiState.collectAsState()
    LaunchedEffect("birthdayScreenKey") {
        when (state.isError) {
            (false) -> {
                showUi(state.eventsInfo)
            }
            else -> {
                val message: String = errorMessage + state.errorText
                showErrorMessage(context, message)
            }
        }
    }
}

private fun showUi(employeeInfos: List<EmployeeInfo>) {
}

private fun showErrorMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}