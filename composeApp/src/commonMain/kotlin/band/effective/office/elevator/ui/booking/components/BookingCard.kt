package band.effective.office.elevator.ui.booking.components

import androidx.compose.material.MaterialTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun BookingCard(roomText: String) {
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
                Image(
                    painter = painterResource(MainRes.images.fluent_desk_regular),
                    contentDescription = null,
                    modifier = Modifier
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