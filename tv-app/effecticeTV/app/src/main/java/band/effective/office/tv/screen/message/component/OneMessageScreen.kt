package band.effective.office.tv.screen.message.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tv.BuildConfig
import band.effective.office.tv.domain.model.message.BotMessage
import band.effective.office.tv.network.AuthInterceptor
import band.effective.office.tv.screen.menu.component.ButtonAutoplay
import band.effective.office.tv.ui.theme.robotoFontFamily
import band.effective.office.tv.utils.calendarToString
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import okhttp3.OkHttpClient

@Composable
fun OneMessageScreen(
    modifier: Modifier,
    imageLoader: ImageLoader,
    message: BotMessage,
    textColor: Color = Color.Black,
    onClickButton: (() -> Unit)? = null
) {
    Column(
        modifier = modifier.padding(horizontal = 100.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column() {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier.clip(CircleShape),
                    imageLoader = imageLoader,
                    model = "https://${BuildConfig.apiMattermostUrl}users/${message.author.id}/image",
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = message.author.name,
                    color = textColor
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = calendarToString(message.start),
                    color = MaterialTheme.colors.primaryVariant
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = message.text,
                fontFamily = robotoFontFamily(),
                fontSize = 20.sp,
                color = textColor
            )
        }
        if (onClickButton != null) {
            Spacer(modifier = Modifier.height(20.dp))
            ButtonAutoplay(
                text = "Закрыть",
                onClick = { onClickButton() }
            )
        }
    }
}