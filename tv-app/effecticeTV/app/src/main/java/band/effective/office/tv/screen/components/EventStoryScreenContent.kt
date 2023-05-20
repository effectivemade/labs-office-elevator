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
import band.effective.office.tv.domain.model.duolingo.DuolingoUser
import band.effective.office.tv.screen.duolingo.DuolingoScreen
import band.effective.office.tv.screen.eventStory.KeySortDuolingoUser
import band.effective.office.tv.screen.eventStory.models.DuolingoUserInfo
import band.effective.office.tv.screen.eventStory.models.EmployeeInfoUI
import band.effective.office.tv.screen.eventStory.models.StoryModel
import band.effective.office.tv.screen.eventStory.models.StoryType
import com.example.effecticetv.ui.theme.IndependentColors

@Composable
fun EventStoryScreenContent(
    eventsInfo: List<StoryModel>,
    currentStoryIndex: Int,
    modifier: Modifier = Modifier,
    onImageLoading: () -> Unit,
    onImageLoaded: () -> Unit,
) {
    Surface(
        modifier = modifier, color = IndependentColors.StoryBackgroundGray
    ) {
        Column {
            StoryIndicator(
                eventsInfo.size,
                currentStoryIndex,
                Modifier
                    .padding(32.dp)
                    .fillMaxWidth()
                    .height(8.dp),
                stories = eventsInfo,
                currentStoryIndex = currentStoryIndex,
            )
            when(val item = eventsInfo[currentStoryIndex].storyType) {
                StoryType.Employee -> {
                    val storyData = eventsInfo[currentStoryIndex]
                    StoryContent(
                        storyData as EmployeeInfoUI,
                        { onImageLoading() }, { onImageLoaded() },
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = 64.dp)
                    )
                }
                StoryType.Duolingo -> {
                    val duolingoItem = eventsInfo[currentStoryIndex] as DuolingoUserInfo
                    DuolingoScreen(
                        keySort = duolingoItem.keySort,
                        duolingoUser = duolingoItem.users
                    )
                }
            }

        }
    }
}
