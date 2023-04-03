package band.effective.office.tv.screen.menu.component

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.effecticetv.ui.theme.robotoFontFamily

@Composable
fun ButtonAutoplay(onFocus: ()->Unit) {
    var isFocus by remember { mutableStateOf(false) }
    //TODO(Artem Gruzdev) replace text in str res
    val animatedBackgroundColor by animateColorAsState(
        targetValue =
        if (isFocus) MaterialTheme.colors.primaryVariant
        else MaterialTheme.colors.secondaryVariant
    )
    Button(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(animatedBackgroundColor),
        modifier = Modifier
            .onFocusChanged { focusState ->
                isFocus = focusState.isFocused
                if (isFocus) onFocus()
            }
            .focusable()
    ) {
        Text(
            text = "autoplay",
            color = Color.White,
            modifier = Modifier
                .padding(horizontal = 27.dp, vertical = 16.dp)
                .alpha(if(isFocus) 1f else 0.5f),
            fontFamily = robotoFontFamily(),
            fontSize = 30.sp
        )
    }
}