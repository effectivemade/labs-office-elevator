package band.effective.office.tv.screen.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tv.R
import band.effective.office.tv.domain.models.Employee.Anniversary
import band.effective.office.tv.domain.models.Employee.Birthday
import band.effective.office.tv.domain.models.Employee.EmployeeInfo
import band.effective.office.tv.domain.models.Employee.EventType
import band.effective.office.tv.domain.models.Employee.NewEmployee
import band.effective.office.tv.utils.getCorrectDeclension
import coil.compose.AsyncImage
import com.example.effecticetv.ui.theme.drukLCGWideMedium
import com.example.effecticetv.ui.theme.museoCyrl

@Composable
fun StoryContent(employeeInfoes: List<EmployeeInfo>, currentStoryIndex: Int, modifier: Modifier) {

    val isAnniversary = employeeInfoes[currentStoryIndex].eventType == EventType.Anniversary
    val isBirthday = employeeInfoes[currentStoryIndex].eventType == EventType.Birthday

    val screenConfiguration = LocalConfiguration.current
    val screenWidth = screenConfiguration.screenWidthDp.dp

    Log.d("StoryContent", "Size of employeeInfo - ${employeeInfoes.size}")

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 64.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .width(500.dp)
                .fillMaxSize()
        ) {
            Text(
                text = employeeInfoes[currentStoryIndex].name + ",",
                fontSize = 64.sp,
                fontFamily = museoCyrl,
                fontStyle = FontStyle.Italic
            )
            if (isAnniversary) {
                val story = employeeInfoes[currentStoryIndex] as Anniversary
                Text(
                    text =
                    stringResource(id = R.string.with_us) + " " + story.yearsInCompany + " " + getCorrectDeclension(
                        story.yearsInCompany,
                        "год",
                        "года",
                        "лет"
                    ),
                    fontSize = 54.sp,
                    fontFamily = drukLCGWideMedium,
                )
            } else if (isBirthday) {
                Text(
                    text = stringResource(id = R.string.congratulations_birthday),
                    fontSize = 54.sp,
                    fontFamily = drukLCGWideMedium,
                )
            } else {
                Text(
                    text = stringResource(id = R.string.now_in_team),
                    fontSize = 54.sp,
                    fontFamily = drukLCGWideMedium,
                )
            }

        }

        AsyncImage(
            model = employeeInfoes[currentStoryIndex].photoUrl,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(300.dp)
                .padding(10.dp)
                .clip(CircleShape),
            contentDescription = "Employee photo"
        )

    }


}

@Composable
@Preview
fun PreviewStoryContent() {
    StoryContent(
        employeeInfoes = listOf(
            NewEmployee("John Doe", "testUrl"),
            Birthday("John Doe", "testUrl"),
            NewEmployee("John Doe", "testUrl"),
            Birthday("John Doe", "testUrl")
        ),
        1,
        Modifier
            .fillMaxSize()
            .padding(vertical = 64.dp)
    )
}