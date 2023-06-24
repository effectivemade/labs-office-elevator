package band.effective.office.tv.screen.autoplayMenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import band.effective.office.tv.R
import band.effective.office.tv.screen.menu.component.MenuComponent
import band.effective.office.tv.screen.menu.component.MenuItemType
import band.effective.office.tv.screen.menu.component.PlayButton
import band.effective.office.tv.screen.navigation.Screen
import band.effective.office.tv.ui.theme.robotoFontFamily

data class ItemRes(
    val text: String,
    val icon: Painter? = null,
    val activeIcon: Painter? = null
) {}

@Composable
fun AutoplayMenuScreen(
    viewModel: AutoplayMenuViewModel = hiltViewModel(), navController: NavController
) {
    var screenList by remember {
        mutableStateOf(
            listOf(
                Screen.Stories,
                Screen.BestPhoto,
                Screen.Events
            )
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.autoplay_menu_title),
            fontFamily = robotoFontFamily(),
            fontSize = 50.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(50.dp))
        MenuComponent(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            itemModifier = Modifier.clip(shape = RoundedCornerShape(20.dp)),
            itemsList = listOf(
                Pair(
                    Screen.Stories,
                    ItemRes(
                        text = stringResource(R.string.story_screen_title),
                        icon = painterResource(R.drawable.icon_stories_orange),
                        activeIcon = painterResource(R.drawable.icon_stories_white)
                    )
                ),
                Pair(
                    Screen.BestPhoto,
                    ItemRes(
                        text = stringResource(R.string.best_photo_screen_title),
                        icon = painterResource(R.drawable.icon_photos_orange),
                        activeIcon = painterResource(R.drawable.icon_photos_white)
                    )
                ),
                Pair(
                    Screen.Events,
                    ItemRes(
                        text = stringResource(R.string.event_screen_title),
                        icon = painterResource(R.drawable.icon_event_orange),
                        activeIcon = painterResource(R.drawable.icon_event_white)
                    )
                ),
            ),
            onNavigate = {},
            menuItemType = MenuItemType.SelectableItem(true) {
                if (it.second) {
                    screenList += it.first
                } else {
                    screenList -= it.first
                }
            })
        Spacer(modifier = Modifier.height(50.dp))
        PlayButton(modifier = Modifier
            .height(70.dp)
            .width(300.dp)
            .clip(shape = RoundedCornerShape(200.dp)),
            text = stringResource(id = R.string.autoplay_menu_button),
            onClick = {
                viewModel.autoplayController.resetController()
                if (screenList.isNotEmpty()) {
                    screenList.forEach { viewModel.autoplayController.registerScreen(it) }
                    navController.navigate(Screen.Autoplay.name)
                }
            })
        Spacer(modifier = Modifier.height(10.dp))
        if (screenList.isEmpty())
            Text(text = stringResource(id = R.string.autoplay_zero_select))
    }
}