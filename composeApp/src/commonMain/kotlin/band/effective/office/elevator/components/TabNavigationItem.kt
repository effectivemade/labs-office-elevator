package band.effective.office.elevator.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import band.effective.office.elevator.lightGray
import band.effective.office.elevator.navigation.Tab
import band.effective.office.elevator.textGrayColor
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun RowScope.TabNavigationItem(
    tab: Tab,
    selected: Boolean,
    onSelect: () -> Unit,
) {
    val selectedColor = MaterialTheme.colors.primary
    val unselectedColor = textGrayColor
    BottomNavigationItem(
        modifier = Modifier,
        selected = selected,
        selectedContentColor = selectedColor,
        unselectedContentColor = unselectedColor,
        onClick = onSelect,
        icon = { Icon(imageVector = tab.icon, contentDescription = stringResource(tab.title)) },
        label = { Text(stringResource (tab.title), color = if (selected) selectedColor else unselectedColor) }
    )
}
