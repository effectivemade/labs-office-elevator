package band.effective.office.elevator.components.auth_components

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun AuthTitle(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign?,
) {
    Text(
        text = text,
        modifier = Modifier
            .wrapContentSize()
            .then(modifier),
        fontSize = 28.sp,
//        fontFamily = FontFamily TODO : Add font
        fontWeight = FontWeight(600),
        color = Color(0xFF5800CB),
        textAlign = textAlign,
        letterSpacing = 0.1.sp
    )
}