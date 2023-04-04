package band.effective.office.tv.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.effecticetv.ui.theme.Typography

private val DarkColorPalette = darkColors(
    primary = Color.White,
    primaryVariant = VividTangelo,
    secondary = QuickSilver,
    secondaryVariant = BlackOlive,
    background = CharlestonGreen
)

@Composable
fun EffecticeTVTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}