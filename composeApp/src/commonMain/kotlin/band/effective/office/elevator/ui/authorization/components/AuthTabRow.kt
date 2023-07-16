package band.effective.office.elevator.ui.authorization.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AuthTabRow(selectedIndex: Int) {
    val i = remember { mutableStateOf(selectedIndex) }
    val tabs = listOf("1", "2", "3")

    TabRow(
        selectedTabIndex = i.value,
        divider = {
            Color.Transparent
        },
        indicator = { pos ->
            TabRowDefaults.Indicator(
                modifier = Modifier
                    .background(Color.Transparent)
                    .height(0.dp),
                color = Color.Transparent
            )
        },
        backgroundColor = Color.Transparent,
        contentColor = Color.Transparent,
        modifier = Modifier
            .background(Color.Transparent)
            .clickable(enabled = false, onClick = {})
    ) {
        tabs.forEachIndexed { index, s ->
            AuthTabItem(i.value == index, index)
        }
    }
}