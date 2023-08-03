package band.effective.office.tablet.ui.freeNegotiationsScreen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.ui.theme.CustomDarkColors

@Composable
fun RoomCharacteristics(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = CustomDarkColors.primaryTextAndIcon,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.subtitle1.copy(
                color = CustomDarkColors.primaryTextAndIcon,
                fontWeight = FontWeight.Medium
            )
        )
    }
}