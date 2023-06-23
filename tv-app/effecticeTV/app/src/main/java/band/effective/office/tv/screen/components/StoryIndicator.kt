package band.effective.office.tv.screen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import band.effective.office.tv.ui.theme.IndependentColors
import band.effective.office.tv.screen.components.ProgressIndicator
@Composable
fun StoryIndicator(countStories: Int, currentStoryIndex: Int, modifier: Modifier) {

    val screenConfiguration = LocalConfiguration.current
    val screenWidth = screenConfiguration.screenWidthDp.dp
    val indicatorWidth = (screenWidth - 64.dp - (countStories - 1) * 8.dp) / (countStories)

    ProgressIndicator(
        modifier = modifier.fillMaxWidth(),
        elementModifier = Modifier.width(indicatorWidth),
        count = countStories, currentIndex = currentStoryIndex,
        elementColor = IndependentColors.StoryActiviteIndicatorGray,
        currentElementColor = IndependentColors.StoryIndicatorGray
    )

}

@Preview
@Composable
fun PreviewStoryIndicator() {
    StoryIndicator(
       5, 1, Modifier.padding(24.dp)
    )
}