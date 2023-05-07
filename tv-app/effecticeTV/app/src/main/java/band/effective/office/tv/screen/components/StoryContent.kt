package band.effective.office.tv.screen.components

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tv.R
import band.effective.office.tv.domain.model.notion.Anniversary
import band.effective.office.tv.domain.model.notion.EventType
import band.effective.office.tv.screen.eventStory.models.AnniversaryUI
import band.effective.office.tv.screen.eventStory.models.EmployeeInfoUI
import band.effective.office.tv.screen.eventStory.models.NewEmployeeUI
import band.effective.office.tv.ui.theme.drukLCGWideMedium
import band.effective.office.tv.ui.theme.museoCyrl
import band.effective.office.tv.utils.getCorrectDeclension
import coil.compose.AsyncImage


@Composable
fun StoryContent(employeeInfo: EmployeeInfoUI, modifier: Modifier) {

    val isAnniversary = employeeInfo.eventType == EventType.Anniversary
    val isBirthday = employeeInfo.eventType == EventType.Birthday
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
                text = employeeInfo.name + ",",
                fontSize = 64.sp,
                fontFamily = museoCyrl,
                color = Color.Black,
                fontStyle = FontStyle.Italic
            )
            if (isAnniversary) {
                val story = employeeInfo as AnniversaryUI
                Text(
                    text =
                    stringResource(id = R.string.with_us) + " " + story.yearsInCompany + " " + getCorrectDeclension(
                        story.yearsInCompany,
                        "год",
                        "года",
                        "лет"
                    ),
                    fontSize = 54.sp,
                    color = Color.Black,
                    fontFamily = drukLCGWideMedium,
                )
            } else if (isBirthday) {
                Text(
                    text = stringResource(id = R.string.congratulations_birthday),
                    fontSize = 54.sp,
                    color = Color.Black,
                    fontFamily = drukLCGWideMedium,
                )
            } else {
                Text(
                    text = stringResource(id = R.string.now_in_team),
                    fontSize = 54.sp,
                    color = Color.Black,
                    fontFamily = drukLCGWideMedium,
                )
            }

        }

        AsyncImage(
            model = employeeInfo.photoUrl,
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
        employeeInfo =
            NewEmployeeUI("John Doe", "testUrl"),
        Modifier
            .fillMaxSize()
            .padding(vertical = 64.dp)
    )
}