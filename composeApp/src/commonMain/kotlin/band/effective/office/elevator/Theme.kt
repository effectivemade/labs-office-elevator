package band.effective.office.elevator

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


private val LightColors = lightColors(
    primary = theme_light_primary,
    onPrimary = theme_light_onPrimary,
    secondary = theme_light_secondary,
    secondaryVariant = theme_light_secondary_variant,
    onSecondary = theme_light_onSecondary,
    error = theme_light_error,
    onError = theme_light_onError,
    background = theme_light_background,
    onBackground = theme_light_onBackground,
    surface = theme_light_surface,
    onSurface = theme_light_onSurface,
)

private val DarkColors = darkColors(
    primary = theme_dark_primary,
    onPrimary = theme_dark_onPrimary,
    secondary = theme_dark_secondary,
    onSecondary = theme_dark_onSecondary,
    error = theme_dark_error,
    onError = theme_dark_onError,
    background = theme_dark_background,
    onBackground = theme_dark_onBackground,
    surface = theme_dark_surface,
    onSurface = theme_dark_onSurface,
)

@Composable
internal fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (!useDarkTheme) {
        LightColors
    } else {
        DarkColors
    }

    MaterialTheme(
        colors = colors,
        content = {
            Surface(content = content)
        }
    )
}
