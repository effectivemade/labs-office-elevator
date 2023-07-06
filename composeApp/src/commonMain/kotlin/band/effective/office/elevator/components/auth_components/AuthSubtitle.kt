package band.effective.office.elevator.components.auth_components

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.theme_light_tertiary_color

@Composable
fun AuthSubTitle(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign?,
) {
    Text(
        text = text,
        modifier = Modifier
            .wrapContentSize()
            .then(modifier),
        fontSize = 16.sp,
//    fontFamily FontFamily TODO : Add font
        fontWeight = FontWeight(500),
        textAlign = textAlign,
        color = theme_light_tertiary_color,
        letterSpacing = 0.1.sp
    )
}