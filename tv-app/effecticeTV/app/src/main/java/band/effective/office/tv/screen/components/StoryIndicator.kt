package band.effective.office.tv.screen.eventStory

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import band.effective.office.tv.domain.models.Employee.Birthday
import band.effective.office.tv.domain.models.Employee.EmployeeInfo
import com.example.effecticetv.ui.theme.EffecticeTVTheme
import com.example.effecticetv.ui.theme.Gray
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun StoryIndicator(stories: List<EmployeeInfo>, currentStoryIndex: Int, modifier: Modifier) {

    val screenConfiguration = LocalConfiguration.current
    val screenWidth = screenConfiguration.screenWidthDp.dp
    val indicatorWidth = (screenWidth - 64.dp - (stories.size - 1) * 8.dp) / (stories.size)

    Row(modifier = modifier) {
        stories.forEachIndexed { index, _ ->
            val animatedColor =
                animateColorAsState(
                    if (index == currentStoryIndex) Color.White else Gray
                ) {

                }
            Surface(
                color = animatedColor.value,
                modifier = Modifier
                    .width(indicatorWidth)
                    .padding(end = 8.dp)
            ) {
                Text("")
            }
        }
    }
}

@Preview
@Composable
fun PreviewStoryIndicator() {
    StoryIndicator(
        listOf(
            Birthday("John Doe", "test"),
            Birthday("John Doe", "test"),
            Birthday("John Doe", "test"),
            Birthday("John Doe", "test"),
            Birthday("John Doe", "test"),
        ), 1, Modifier.padding(24.dp)
    )
}