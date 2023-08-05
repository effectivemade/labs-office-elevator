package band.effective.office.elevator.ui.booking.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedTheme
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.textInBorderPurple
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun BookingCard(roomText: String, onClickOpenBookAccept: () -> Unit) {
    Column (modifier = Modifier.padding(bottom = 16.dp).clickable {  onClickOpenBookAccept()}){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White, shape = RoundedCornerShape(size = 16.dp))
                .padding(all = 16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(100.dp))
                        .background(color = ExtendedTheme.colors.purple_heart_100)
                        .padding(all = 8.dp)
                        .size(44.dp)
                ) {
                    Icon(
                        painter = painterResource(MainRes.images.table_icon),
                        contentDescription = null,
                        modifier = Modifier,
                        tint = textInBorderPurple
                    )
                }
                Text(
                    text = roomText,
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight(500)
                    )
                )
            }
        }
    }
}