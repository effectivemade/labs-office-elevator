package band.effective.office.tv.screen.menu.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tv.screen.navigation.NavigationModel
import band.effective.office.tv.screen.navigation.Screen

@Composable
fun MenuComponent(
    itemsList: List<Pair<Screen,String>>,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
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
            MenuItem(
                text = item.second,
                onClick = { onNavigate(item.first) },
                onFocus = {
                    weight = if (it) 1.1f else 1f
                },
                modifier = Modifier.weight(weight)
            )
            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}