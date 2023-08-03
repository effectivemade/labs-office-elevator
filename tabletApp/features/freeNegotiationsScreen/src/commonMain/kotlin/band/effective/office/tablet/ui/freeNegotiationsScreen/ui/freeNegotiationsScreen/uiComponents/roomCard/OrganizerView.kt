package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents.roomCard

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette


@Composable
fun OrganizerView(organizer: String){
    Text(
        text = organizer,
        style = MaterialTheme.typography.h5,
        color = LocalCustomColorsPalette.current.primaryTextAndIcon,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}