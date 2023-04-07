package band.effective.office.tv.screen.eventStory

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import band.effective.office.tv.R
import band.effective.office.tv.view.uiStateModels.LatestEventInfoUiState
import band.effective.office.tv.domain.models.Employee.EmployeeInfo
import band.effective.office.tv.view.viewmodel.EventStoryViewModel

@Composable
fun EventStoryScreen() {

    val context = LocalContext.current
    val errorMessage = stringResource(id = R.string.error_occurred)


    val viewModel = hiltViewModel<EventStoryViewModel>()
    LaunchedEffect("birthdayScreenKey") {
        viewModel.uiState.collect {
            viewModel.uiState.collect { state ->
                when (state) {
                    is LatestEventInfoUiState.Success -> showUi(state.employeeInfos)
                    is LatestEventInfoUiState.Error -> {
                        val message: String = errorMessage + state.exception
                        showErrorMessage(context, message)
                    }
                }
            }
        }
    }
}

private fun showUi(employeeInfos: List<EmployeeInfo>) {
    //TODO(Parkhomenko Egor): write UI logic here
}

private fun showErrorMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}