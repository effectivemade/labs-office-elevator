package band.effective.office.tv.screen.leaderIdEvets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import com.example.effecticetv.ui.theme.CaptionColor
import com.example.effecticetv.ui.theme.robotoFontFamily

@Composable
fun TextWithCaptionAndIcon(
    resourceId: Int = -1,
    text: String = "",
    caption: String = "",
    fontSize: TextUnit = 10.sp,
    iconSize: Dp = 10.dp,
    modifier: Modifier = Modifier
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        if (resourceId != -1) {
            Image(
                painter = painterResource(resourceId),
                contentDescription = "text",
                modifier = Modifier
                    .height(iconSize)
                    .padding(end = 10.dp)
            )
        }
        if (caption != "") {
            Text(
                text = "$caption: ",
                color = CaptionColor,
                fontSize = fontSize,
                fontFamily = robotoFontFamily()
            )
        }
        Text(
            text = text, color = Color.White, fontSize = fontSize, fontFamily = robotoFontFamily()
        )
    }
}