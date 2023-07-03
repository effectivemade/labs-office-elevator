package band.effective.office.tablet.ui.mainScreen.components.roomInfoComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DateTimeView(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(text = "17:49", color = Color(0xFFFAFAFA), fontSize = 30.sp)
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "28 июня, среда", color = Color(0xFFFAFAFA), fontSize = 25.sp)
    }
}