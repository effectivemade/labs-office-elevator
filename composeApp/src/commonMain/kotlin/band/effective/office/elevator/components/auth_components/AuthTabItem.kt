package band.effective.office.elevator.components.auth_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.theme_light_secondary_icon_color
import band.effective.office.elevator.theme_light_tertiary_icon_color

@Composable
fun AuthTabItem(selected: Boolean) {

    val selectedColor = remember { mutableStateOf(theme_light_secondary_icon_color) }
    val unselectedColor = remember { mutableStateOf(theme_light_tertiary_icon_color) }

    Tab(
        selectedContentColor = selectedColor.value,
        unselectedContentColor = unselectedColor.value,
        selected = selected,
        onClick = { },
        enabled = false,
        modifier = Modifier
            .height(8.dp),
        icon = {
            Box(modifier = Modifier.padding(horizontal = 8.dp).clip(RoundedCornerShape(8.dp))) {
                Divider(
                    modifier = Modifier.height(8.dp),
                    color = if (selected) selectedColor.value else unselectedColor.value
                )
            }
        }
    )
}