package band.effective.office.tv.screen.menu.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tv.screen.autoplayMenu.component.SelectableMenuItem
import band.effective.office.tv.screen.navigation.Screen

sealed class MenuItemType {
    object SimpleItem : MenuItemType()
    data class SelectableItem(val onCheckedChange: (Boolean) -> Unit) : MenuItemType()
}

@Composable
fun MenuComponent(
    itemsList: List<Pair<Screen, String>>,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier,
    menuItemType: MenuItemType = MenuItemType.SimpleItem
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(20.dp))
        for (item in itemsList) {
            var weight by remember { mutableStateOf(1f) }
            when (menuItemType) {
                is MenuItemType.SimpleItem -> MenuItem(
                    modifier = Modifier.weight(weight),
                    text = item.second,
                    onClick = { onNavigate(item.first) },
                    onFocus = {
                        weight = if (it) 1.1f else 1f
                    }
                )
                is MenuItemType.SelectableItem -> SelectableMenuItem(
                    modifier = Modifier.weight(weight),
                    text = item.second,
                    onClick = { onNavigate(item.first) },
                    onFocus = {
                        weight = if (it) 1.1f else 1f
                    },
                    onCheckedChange = {menuItemType.onCheckedChange(it)}
                    )
            }

            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}