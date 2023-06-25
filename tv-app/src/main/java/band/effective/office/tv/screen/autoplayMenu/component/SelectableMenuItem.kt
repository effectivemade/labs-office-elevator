package band.effective.office.tv.screen.autoplayMenu.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import band.effective.office.tv.screen.menu.component.MenuItem

@Composable
fun SelectableMenuItem(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onFocus: (Boolean) -> Unit,
    onCheckedChange: (Boolean) -> Unit
) {
    var isCheck by remember { mutableStateOf(false) }
    val checkboxColors = CheckboxDefaults.colors(
        checkedColor = MaterialTheme.colors.primaryVariant,
        checkmarkColor = MaterialTheme.colors.secondaryVariant
    )
    MenuItem(text, modifier, {
        isCheck = !isCheck
        onCheckedChange(isCheck)
        onClick()
    }, { onFocus(it) }) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd
        ) { Checkbox(checked = isCheck, onCheckedChange = { }, colors = checkboxColors) }
    }
}