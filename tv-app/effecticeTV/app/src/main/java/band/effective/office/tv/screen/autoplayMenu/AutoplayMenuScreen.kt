package band.effective.office.tv.screen.autoplayMenu

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import band.effective.office.tv.screen.autoplay.userSelect
import band.effective.office.tv.screen.leaderIdEvents.LeaderIdEventsViewModel
import band.effective.office.tv.screen.menu.component.ButtonAutoplay
import band.effective.office.tv.screen.menu.component.MenuComponent
import band.effective.office.tv.screen.menu.component.MenuItemType
import band.effective.office.tv.screen.navigation.Screen
import band.effective.office.tv.screen.photo.PhotoViewModel

@Composable
fun AutoplayMenuScreen(navController: NavController) {
    val pairsScreenVM = listOf(
        Pair(Screen.BestPhoto, hiltViewModel<PhotoViewModel>()),
        Pair(Screen.Events, hiltViewModel<LeaderIdEventsViewModel>())
    )
    var mutableScreenList = mutableListOf<Screen>()
    Column {
        Text(text = "Настройки autoplay")
        MenuComponent(itemsList = listOf(
            Pair(Screen.BestPhoto, "Photo"), Pair(Screen.Events, "Events")
        ), onNavigate = {}, menuItemType = MenuItemType.SelectableItem {
            if (it.second) {
                mutableScreenList.add(it.first)
            } else {
                mutableScreenList.remove(it.first)
            }
        })
        ButtonAutoplay(text = "play", onClick = {
            if (mutableScreenList.isNotEmpty()){
                var mutableMap = mutableMapOf<Screen, ViewModel>()
                pairsScreenVM.forEach { if (mutableScreenList.contains(it.first)) mutableMap[it.first] = it.second }
                userSelect.viewModels = mutableMap
                navController.navigate(Screen.Autoplay.name)
            }
        })
    }
}