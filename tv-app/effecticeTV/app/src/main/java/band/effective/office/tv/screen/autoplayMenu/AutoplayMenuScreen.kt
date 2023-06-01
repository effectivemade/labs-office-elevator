package band.effective.office.tv.screen.autoplayMenu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import band.effective.office.tv.R
import band.effective.office.tv.screen.menu.component.ButtonAutoplay
import band.effective.office.tv.screen.menu.component.MenuComponent
import band.effective.office.tv.screen.menu.component.MenuItemType
import band.effective.office.tv.screen.navigation.Screen
import band.effective.office.tv.ui.theme.robotoFontFamily

@Composable
fun AutoplayMenuScreen(viewModel: AutoplayMenuViewModel = hiltViewModel(),navController: NavController) {
    var mutableScreenList = mutableListOf<Screen>()
    Column(
        modifier = Modifier.padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.autoplay_menu_title),
            fontFamily = robotoFontFamily(),
            fontSize = 50.sp,
            color = Color.White
        )
        MenuComponent(
            modifier = Modifier
                .padding(horizontal = 100.dp)
                .fillMaxHeight(0.8f),
            itemsList = listOf(
                Pair(Screen.Stories, "Stories"),
                Pair(Screen.BestPhoto, stringResource(R.string.best_photo_screen_title)),
                Pair(Screen.Events, stringResource(R.string.event_screen_title)),
            ), onNavigate = {}, menuItemType = MenuItemType.SelectableItem {
                if (it.second) {
                    mutableScreenList.add(it.first)
                } else {
                    mutableScreenList.remove(it.first)
                }
            })
        ButtonAutoplay(text = stringResource(R.string.autoplay_menu_button), onClick = {
            if (mutableScreenList.isNotEmpty()) {
                mutableScreenList.forEach { viewModel.autoplayController.registerScreen(it) }
                navController.navigate(Screen.Autoplay.name)
            }
        })
    }
}