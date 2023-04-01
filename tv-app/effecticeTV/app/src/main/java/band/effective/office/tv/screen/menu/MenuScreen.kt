package band.effective.office.tv.screen.menu

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.items
import band.effective.office.tv.screen.menu.component.MenuItem
import band.effective.office.tv.screen.navigation.NavigationModel
import band.effective.office.tv.ui.theme.BackgroundColor
import band.effective.office.tv.ui.theme.CaptionColor
import band.effective.office.tv.ui.theme.VividTangelo

@Composable
fun MenuScreen(
    itemsList: List<NavigationModel>,
    navController: NavController = rememberNavController()
) {
    Box(
        modifier = Modifier
            .background(BackgroundColor)
            .fillMaxSize(),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TvLazyRow(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally)
                    .animateContentSize(),
                contentPadding = PaddingValues(horizontal = 50.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                items(itemsList) {
                    MenuItem(
                        text = it.title,
                        focusedBackgroundColor = VividTangelo,
                        unFocusedBackgroundColor = CaptionColor,
                        onClick = { navController.navigate(it.screen.name) }
                    )
                }
            }
        }
    }
}