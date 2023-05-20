package band.effective.office.tv.screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tv.domain.model.notion.EmployeeInfo
import com.example.effecticetv.ui.theme.IndependentColors

@Composable
fun EventStoryScreenContent(
    modifier: Modifier = Modifier,
    eventsInfo: List<EmployeeInfo>,
    currentStoryIndex: Int,
    onImageLoading: () -> Unit,
    onImageLoaded: () -> Unit,
) {
    Surface(
        modifier = modifier, color = IndependentColors.StoryBackgroundGray
    ) {
        Column {
            StoryIndicator(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth()
                    .height(8.dp),
                stories = eventsInfo,
                currentStoryIndex = currentStoryIndex,
            )
            StoryContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 64.dp),
                employeeInfoes = eventsInfo,
                currentStoryIndex = currentStoryIndex,
                onImageLoading = { onImageLoading() },
                onImageLoaded = { onImageLoaded() },
            )
        }
    }
}
