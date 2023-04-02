package band.effective.office.tv.screen.menu.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import band.effective.office.tv.ui.theme.CaptionColor
import band.effective.office.tv.ui.theme.VividTangelo

@Composable
fun MenuItem(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onFocus: (Boolean) -> Unit
) {
    var isSelect by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .background(if (isSelect) VividTangelo else CaptionColor)
            .fillMaxHeight(if (isSelect) 0.95f else 0.9f)
            .onFocusChanged {
                isSelect = it.isFocused
                onFocus(it.isFocused) }
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        androidx.tv.material3.Text(
            text = text,
            color = Color.White,
            fontSize = 30.sp
        )
    }
}