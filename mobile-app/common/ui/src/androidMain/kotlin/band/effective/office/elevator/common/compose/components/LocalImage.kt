package band.effective.office.elevator.common.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

@Composable
actual fun LocalImage(
    imageResourceName: String,
    modifier: Modifier,
    contentDescription: String?
) {
    val context = LocalContext.current
    val imageRes =
        context.resources.getIdentifier(imageResourceName, "drawable", context.packageName)
            .takeIf { it != 0 }
    if (imageRes != null) {
        androidx.compose.foundation.Image(
            modifier = modifier,
            painter = painterResource(id = imageRes),
            contentDescription = contentDescription,
            contentScale = ContentScale.FillWidth,
        )
    } else {
        Row(
            modifier = modifier.background(
                MaterialTheme.colors.primary,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = contentDescription,
                tint = Color.White,
            )
            Text(
                "Image not supported",
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}