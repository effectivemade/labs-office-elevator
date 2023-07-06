package band.effective.office.elevator.components.auth_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.theme_light_secondary_icon_color
import band.effective.office.elevator.theme_light_tertiary_icon_color

@Composable
fun AuthTabItem(selected: Boolean, onSelect: () -> Unit) {

    val selectedColor = theme_light_secondary_icon_color
    val unselectedColor = theme_light_tertiary_icon_color

    Tab(
        selectedContentColor = selectedColor,
        unselectedContentColor = unselectedColor,
        selected = selected,
        onClick = onSelect,
        enabled = false,
        modifier = Modifier
            .height(8.dp),
        icon = {
            Box(modifier = Modifier) {
                Icon(
                    imageVector = AuthTabIcon,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    )
}