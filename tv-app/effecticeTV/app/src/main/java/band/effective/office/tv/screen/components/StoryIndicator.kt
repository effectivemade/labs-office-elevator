package band.effective.office.tv.screen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import band.effective.office.tv.domain.model.notion.Birthday
import band.effective.office.tv.domain.model.notion.EmployeeInfo
import com.example.effecticetv.ui.theme.IndependentColors

@Composable
fun StoryIndicator(modifier: Modifier, stories: List<EmployeeInfo>, currentStoryIndex: Int) {

    val screenConfiguration = LocalConfiguration.current
    val screenWidth = screenConfiguration.screenWidthDp.dp
    val indicatorWidth = (screenWidth - 64.dp - (stories.size - 1) * 8.dp) / (stories.size)

    Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
        stories.forEachIndexed { index, _ ->
            val animatedColor =
                animateColorAsState(
                    if (index == currentStoryIndex) IndependentColors.StoryActiviteIndicatoGray else IndependentColors.StoryIndicatorGray
                )
            Box(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .width(indicatorWidth)
                    .height(8.dp)
                    .background(animatedColor.value)
            )
        }
    }
}

@Preview
@Composable
fun PreviewStoryIndicator() {
    StoryIndicator(
        modifier = Modifier.padding(24.dp),
        stories =
        listOf(
            Birthday("John Doe", "test"),
            Birthday("John Doe", "test"),
            Birthday("John Doe", "test"),
            Birthday("John Doe", "test"),
            Birthday("John Doe", "test"),
        ),
        currentStoryIndex = 1,
    )
}