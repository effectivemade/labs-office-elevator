package band.effective.office.tv.screen.autoplayMenu

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import band.effective.office.tv.screen.menu.component.ButtonAutoplay
import band.effective.office.tv.screen.menu.component.MenuComponent
import band.effective.office.tv.screen.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun AutoplayMenuScreen() {
    Column{
        Text(text = "Настройки autoplay")
        MenuComponent(
            itemsList = listOf(
                Pair(Screen.BestPhoto, "Photo"),
                Pair(Screen.Events, "Events")
            ), onNavigate = {})
        ButtonAutoplay (
            text = "play",
            onClick = {}
        )
    }

}