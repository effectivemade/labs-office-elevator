package band.effective.office.tv.screen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import band.effective.office.tv.ui.theme.IndependentColors

@Composable
fun StoryIndicator(countStories: Int, currentStoryIndex: Int, modifier: Modifier) {

    val screenConfiguration = LocalConfiguration.current
    val screenWidth = screenConfiguration.screenWidthDp.dp
    val indicatorWidth = (screenWidth - 64.dp - (countStories - 1) * 8.dp) / (countStories)

    LazyRow(modifier = modifier, horizontalArrangement = Arrangement.Center) {
        items(countStories) { index ->
            val animatedColor =
                animateColorAsState(
                    if (index == currentStoryIndex) IndependentColors.StoryActiviteIndicatorGray else IndependentColors.StoryIndicatorGray
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
       5, 1, Modifier.padding(24.dp)
    )
}