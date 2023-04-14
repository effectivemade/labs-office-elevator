package band.effective.office.tv.screen.autoplayMenu.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.sp
import com.example.effecticetv.ui.theme.robotoFontFamily

@Composable
fun SelectableMenuItem(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onFocus: (Boolean) -> Unit,
    onCheckedChange: (Boolean) -> Unit
) {
    var isFocus by remember { mutableStateOf(false) }
    var isCheck by remember { mutableStateOf(false) }

    val animatedBackgroundColor by animateColorAsState(
        targetValue =
        if (isFocus) MaterialTheme.colors.primaryVariant
        else MaterialTheme.colors.secondary
    )

    Box(modifier = modifier
        .graphicsLayer {
            scaleX =
                if (isFocus) 1.1f
                else 1f
            scaleY =
                if (isFocus) 1.1f
                else 1f
        }
        .background(animatedBackgroundColor)
        .fillMaxHeight(0.9f)
        .onFocusChanged {
            isFocus = it.isFocused
            onFocus(it.isFocused)
        }
        .clickable {
            isCheck = !isCheck
            onCheckedChange(isCheck)
            onClick()
        },
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd) { Checkbox(checked = isCheck, onCheckedChange = { }) }
        androidx.tv.material3.Text(
            text = text, color = Color.White, fontSize = 30.sp, fontFamily = robotoFontFamily()
        )
    }
}