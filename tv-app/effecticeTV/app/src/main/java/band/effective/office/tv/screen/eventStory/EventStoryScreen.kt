package band.effective.office.tv.screen.eventStory

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import band.effective.office.tv.domain.LatestEventInfoUiState
import band.effective.office.tv.domain.models.Employee.EmployeeInfo
import band.effective.office.tv.view.viewmodel.EventStoryViewModel

@Composable
fun EventStoryScreen() {
    val viewModel = hiltViewModel<EventStoryViewModel>()
    LaunchedEffect("birthdayScreenKey") {
        viewModel.uiState.collect {
            viewModel.uiState.collect { state ->
                when (state) {
                    is LatestEventInfoUiState.Success -> showUi(state.employeeInfos)
                    is LatestEventInfoUiState.Error -> Log.d(
                        "BirthdayScreen",
                        "Error occurred ${state.exception}"
                    )
                }
            }
        }
    }
}

private fun showUi(employeeInfos: List<EmployeeInfo>) {
    //Need to write ui logic here
}