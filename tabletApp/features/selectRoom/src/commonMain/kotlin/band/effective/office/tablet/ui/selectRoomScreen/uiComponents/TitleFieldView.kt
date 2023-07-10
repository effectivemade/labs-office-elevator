package band.effective.office.tablet.ui.selectRoomScreen.uiComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleFieldView(modifier: Modifier, title: String) {
    Box(
        modifier = modifier.padding(0.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight(500),
            fontFamily = FontFamily.SansSerif,
            color = Color(0xFF808080)
        )
    }
}