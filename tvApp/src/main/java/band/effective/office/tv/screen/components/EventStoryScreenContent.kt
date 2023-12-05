package band.effective.office.tv.screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tv.screen.duolingo.DuolingoScreen
import band.effective.office.tv.screen.eventStory.models.DuolingoUserInfo
import band.effective.office.tv.screen.eventStory.models.EmployeeInfoUI
import band.effective.office.tv.screen.eventStory.models.MessageInfo
import band.effective.office.tv.screen.eventStory.models.NewYearCounter
import band.effective.office.tv.screen.eventStory.models.StoryModel
import band.effective.office.tv.screen.eventStory.models.StoryType
import band.effective.office.tv.screen.message.component.OneMessageScreen
import band.effective.office.tv.ui.theme.IndependentColors
import coil.ImageLoader

@Composable
fun EventStoryScreenContent(
    eventsInfo: List<StoryModel>,
    currentStoryIndex: Int,
    storyProgress: Float = 1f,
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader,
    onImageLoading: () -> Unit,
    onImageLoaded: () -> Unit,
) {
    Surface(
        modifier = modifier, color = IndependentColors.StoryBackgroundGray
    ) {
        Column {
            StoryIndicator(
                countStories = eventsInfo.size,
                currentStoryIndex = currentStoryIndex,
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth()
                    .height(8.dp),
                progress = storyProgress
            )
            when (eventsInfo[currentStoryIndex].storyType) {
                StoryType.Employee -> {
                    val storyData = eventsInfo[currentStoryIndex]
                    StoryContent(
                        employeeInfo = storyData as EmployeeInfoUI,
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = 64.dp)
                    )
                }

                StoryType.Duolingo -> {
                    val duolingoItem = eventsInfo[currentStoryIndex] as DuolingoUserInfo
                    DuolingoScreen(
                        keySort = duolingoItem.keySort,
                        duolingoUser = duolingoItem.users.take(10)
                    )
                }

                StoryType.Message -> {
                    val messageItem = eventsInfo[currentStoryIndex] as MessageInfo
                    OneMessageScreen(
                        modifier = modifier,
                        imageLoader = imageLoader,
                        message = messageItem.message
                    )
                }

                StoryType.NewYear -> NewYearTimer(counter = (eventsInfo[currentStoryIndex] as NewYearCounter))
            }

        }
    }
}

@Composable
private fun NewYearTimer(counter: NewYearCounter) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "${counter.day}:${counter.hour}:${counter.min}:${counter.sec}",
            color = Color.Black,
            fontSize = 50.sp
        )
    }
}
