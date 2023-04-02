package band.effective.office.tv.screen.menu.component

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.effecticetv.ui.theme.robotoFontFamily

@Composable
fun MenuItem(
    text: String,
    focusedBackgroundColor: Color,
    unFocusedBackgroundColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    var isFocus by remember { mutableStateOf(false) }
    val animatedBackgroundColor by animateColorAsState(
        targetValue =
        if (isFocus) focusedBackgroundColor
        else unFocusedBackgroundColor
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX =
                    if (isFocus) 1.1f
                    else 1f
                scaleY =
                    if (isFocus) 1.1f
                    else 1f
            }
            .background(animatedBackgroundColor)
            .size(width = 425.dp, height = 500.dp)
            .onFocusChanged {
                isFocus = it.isFocused
            }
            .focusable()
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 40.sp,
            color = Color.White,
            fontFamily = robotoFontFamily()
        )
    }
}