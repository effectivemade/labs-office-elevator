package band.effective.office.tv.screen.menu.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tv.R
import band.effective.office.tv.screen.navigation.Screen
import band.effective.office.tv.ui.theme.robotoFontFamily

@Composable
fun ButtonAutoplay(
    modifier: Modifier = Modifier,
    onFocus: () -> Unit = {},
    onClick: () -> Unit = {},
    content: @Composable (Boolean) -> Unit = {}
) {
    var isFocus by remember { mutableStateOf(false) }
    val animatedBackgroundColor by animateColorAsState(
        targetValue = if (isFocus) MaterialTheme.colors.primaryVariant
        else MaterialTheme.colors.secondaryVariant
    )
    Button(onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(animatedBackgroundColor),
        modifier = modifier
            .onFocusChanged { focusState ->
                isFocus = focusState.isFocused
                if (isFocus) onFocus()
            }
            .focusable()) {
        content(isFocus)
    }
}