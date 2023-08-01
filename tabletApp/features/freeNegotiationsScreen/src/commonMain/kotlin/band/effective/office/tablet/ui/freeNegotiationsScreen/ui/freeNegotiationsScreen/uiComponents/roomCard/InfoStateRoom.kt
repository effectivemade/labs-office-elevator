package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents.roomCard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.features.freeNegotiationsScreen.MainRes
import band.effective.office.tablet.ui.theme.roomInfoColor

@Composable
fun InfoStateRoom(state: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(80.dp))
            .background(color)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
            text = state,
            style = MaterialTheme.typography.h5,
            color = roomInfoColor
        )
    }
}

fun getMonth(month: Int): String{
    val m = month + 1
    return if(m < 10) MainRes.string.month.format(month = m.toString()) else m.toString()
}