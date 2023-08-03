package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.features.freeNegotiationsScreen.MainRes
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette

@Composable
fun ButtonBackOnMain(
    modifier: Modifier,
    nameRoom: String,
    onMainScreen: (reset: Boolean) -> Unit
) {
    Button(
        modifier = modifier,
        elevation = ButtonDefaults.elevation(0.dp),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.surface),
        onClick = {
            onMainScreen(false)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(MainRes.image.arrow_to_left),
                    contentDescription = "back",
                    modifier = Modifier.size(25.dp),
                    tint = LocalCustomColorsPalette.current.primaryTextAndIcon
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = nameRoom,
                    style = MaterialTheme.typography.h4,
                    color = LocalCustomColorsPalette.current.primaryTextAndIcon,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}