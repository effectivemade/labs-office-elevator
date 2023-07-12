package band.effective.office.tablet.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val darkColors = darkColors(
    primary = band.effective.office.tablet.ui.theme.md_theme_dark_primary,
    secondary = band.effective.office.tablet.ui.theme.md_theme_dark_secondary,
    background = band.effective.office.tablet.ui.theme.md_theme_dark_background,
    surface = band.effective.office.tablet.ui.theme.md_theme_dark_surface,
    onError = band.effective.office.tablet.ui.theme.md_theme_dark_onError,
)

object CustomDarkColors {
    val elevationBackground = md_theme_dark_elevationBackground
    val mountainBackground = md_theme_dark_mountainBackground
    val busyStatus = md_theme_dark_busyStatus
    val freeStatus = md_theme_dark_freeStatus
    val onSuccess = md_theme_dark_onSuccess
    val secondaryButton = md_theme_dark_secondaryButton
    val primaryTextAndIcon = md_theme_dark_primaryTextAndIcon
    val secondaryTextAndIcon = md_theme_dark_secondaryTextAndIcon
    val tertiaryTextAndIcon = md_theme_dark_tertiaryTextAndIcon
    val pressedPrimaryButton = md_theme_dark_pressedPrimaryButton
    val disabledPrimaryButton = md_theme_dark_disabledPrimaryButton
}

private val lightColors = lightColors(
    primary = band.effective.office.tablet.ui.theme.md_theme_light_primary,
    secondary = band.effective.office.tablet.ui.theme.md_theme_light_secondary,
    background = band.effective.office.tablet.ui.theme.md_theme_light_background,
    surface = band.effective.office.tablet.ui.theme.md_theme_light_surface,
    onError = band.effective.office.tablet.ui.theme.md_theme_light_onError,
)

object CustomLightColors {
    val elevationBackground = md_theme_light_elevationBackground
    val mountainBackground = md_theme_light_mountainBackground
    val busyStatus = md_theme_light_busyStatus
    val freeStatus = md_theme_light_freeStatus
    val onSuccess = md_theme_light_onSuccess
    val secondaryButton = md_theme_light_secondaryButton
    val primaryTextAndIcon = md_theme_light_primaryTextAndIcon
    val secondaryTextAndIcon = md_theme_light_secondaryTextAndIcon
    val tertiaryTextAndIcon = md_theme_light_tertiaryTextAndIcon
    val pressedPrimaryButton = md_theme_light_pressedPrimaryButton
    val disabledPrimaryButton = md_theme_light_disabledPrimaryButton
}

@Composable
internal fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = darkColors

    MaterialTheme(
        colors = colors,
        content = {
            Surface (content = content)
        }
    )
}