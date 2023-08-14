package band.effective.office.tablet.ui.mainScreen.roomInfoComponents.uiComponent

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette

@Composable
fun IconSettingsView(
    modifier: Modifier,
    onSettings: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = { onSettings() },
            modifier = Modifier
                .size(55.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(MainRes.image.settings),
                contentDescription = "Settings",
                modifier = Modifier.size(35.dp),
                tint = LocalCustomColorsPalette.current.primaryTextAndIcon
            )
        }
    }
}