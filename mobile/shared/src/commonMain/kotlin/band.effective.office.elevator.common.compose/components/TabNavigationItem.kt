package band.effective.office.elevator.common.compose.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import band.effective.office.elevator.common.compose.SafeArea
import band.effective.office.elevator.common.compose.navigation.tabs.internal.LocalTabNavigator
import band.effective.office.elevator.common.compose.navigation.tabs.internal.Tab
import band.effective.office.elevator.common.compose.theme.colors.unselectedColor

@Composable
internal fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        BottomNavigationItem(
            modifier = Modifier.padding(bottom = SafeArea.current.value.calculateBottomPadding()),
            selected = tabNavigator.current == tab,
            selectedContentColor = MaterialTheme.colors.secondary,
            unselectedContentColor = unselectedColor,
            onClick = { tabNavigator.current = tab },
            icon = { tab.icon?.let { Icon(painter = it, contentDescription = tab.title) } },
            label = { Text(text = tab.title) }
        )
    }
}