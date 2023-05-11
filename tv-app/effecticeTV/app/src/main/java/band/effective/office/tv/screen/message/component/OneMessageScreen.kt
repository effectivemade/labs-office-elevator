package band.effective.office.tv.screen.message.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tv.BuildConfig
import band.effective.office.tv.domain.model.message.BotMessage
import band.effective.office.tv.utils.calendarToString
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.effecticetv.ui.theme.robotoFontFamily

@Composable
fun OneMessageScreen(modifier: Modifier, message: BotMessage) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 100.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(modifier = Modifier) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier.clip(CircleShape),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://${BuildConfig.apiMattermostUrl}users/${message.author.id}/image")
                        .addHeader("Authorization", "Bearer ${BuildConfig.mattermostBotToken}")
                        .build(),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = message.author.name)
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = calendarToString(message.finish),
                    color = MaterialTheme.colors.primaryVariant
                )
            }
            Spacer(modifier = Modifier.width(100.dp))
            Text(
                text = message.text,
                fontFamily = robotoFontFamily(),
                fontSize = 20.sp
            )
        }
    }
}