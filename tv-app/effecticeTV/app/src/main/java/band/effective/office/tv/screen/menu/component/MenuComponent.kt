package band.effective.office.tv.screen.menu.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.items
import band.effective.office.tv.screen.navigation.NavigationModel
import band.effective.office.tv.ui.theme.CaptionColor
import band.effective.office.tv.ui.theme.VividTangelo

@Composable
fun MenuComponent(
    itemsList: List<NavigationModel>,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TvLazyRow(
        modifier = modifier
            .fillMaxSize()
            .animateContentSize(),
        contentPadding = PaddingValues(horizontal = 50.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        items(itemsList) {
            MenuItem(
                text = it.title,
                focusedBackgroundColor = VividTangelo,
                unFocusedBackgroundColor = CaptionColor,
                onClick = { onNavigate(it.screen.name) }
            )
            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}