package band.effective.office.tv.screen.eventStory

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import band.effective.office.tv.R
import band.effective.office.tv.screen.components.EventStoryScreenContent

@Composable
fun EventStoryScreen(viewModel: EventStoryViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val errorMessage = stringResource(id = R.string.error_occurred)

    val state by viewModel.uiState.collectAsState()
    when {
        state.isLoading -> LoadScreen()
        state.isError -> showErrorMessage(context, errorMessage + state.errorText)
        state.isLoaded -> EventStoryScreenContent(state.eventsInfo, state.currentStoryIndex)
    }

}

//TODO (ParkhomenkoEgor): Change toast to custom component
private fun showErrorMessage(context: Context, message: String) {
    Log.d("EventStoryScreen", message)
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

//TODO (ParkhomenkoEgor): Add LoadScreen
private fun LoadScreen() {

}