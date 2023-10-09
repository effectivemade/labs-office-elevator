package band.effective.office.tv.screen.menu.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import band.effective.office.tv.screen.autoplayMenu.ItemRes
import band.effective.office.tv.screen.autoplayMenu.component.SelectableMenuItem
import band.effective.office.tv.screen.navigation.Screen

sealed class MenuItemType {
    object SimpleItem : MenuItemType()
    data class SelectableItem(
        val defaultState: (Screen) -> Boolean,
        val onCheckedChange: (Pair<Screen, Boolean>) -> Unit
    ) : MenuItemType()
}

@Composable
fun MenuComponent(
    modifier: Modifier = Modifier,
    itemModifier: Modifier = Modifier,
    itemsList: List<Pair<Screen, ItemRes>>,
    onNavigate: (Screen) -> Unit,
    menuItemType: MenuItemType = MenuItemType.SimpleItem
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(20.dp))
        for (item in itemsList) {
            var weight by remember { mutableStateOf(1f) }
            when (menuItemType) {
                is MenuItemType.SimpleItem -> TextMenuItem(
                    modifier = itemModifier.weight(weight),
                    text = item.second.text,
                    onClick = { onNavigate(item.first) },
                    onFocus = {
                        weight = if (it) 1.1f else 1f
                    }
                )
                is MenuItemType.SelectableItem -> SelectableMenuItem(
                    modifier = itemModifier.weight(weight),
                    res = item.second,
                    onClick = { onNavigate(item.first) },
                    onFocus = {
                        weight = if (it) 1.1f else 1f
                    },
                    onCheckedChange = { menuItemType.onCheckedChange(Pair(item.first,it)) },
                    defaultState = menuItemType.defaultState(item.first)
                )
            }

            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}