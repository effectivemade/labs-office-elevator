package band.effective.office.tablet.ui.selectRoomScreen.uiComponents

import androidx.compose.foundation.background
import androidx.compose.material.Icon
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.features.selectRoom.MainRes
import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.ui.theme.CustomDarkColors
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette

@Composable
fun CrossButtonView(modifier: Modifier, onDismissRequest:() -> Unit) {

    Box(
        modifier = modifier.padding(end = 42.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        IconButton(
            onClick = { onDismissRequest() },
            modifier = Modifier
                .size(40.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(MainRes.image.cross),
                contentDescription = "Cross",
                modifier = Modifier.size(25.dp),
                tint = LocalCustomColorsPalette.current.secondaryTextAndIcon
            )
        }
    }
}