package band.effective.office.tablet.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val DarkColors = AppThemeColors(
    onPrimary = md_theme_dark_onPrimary,
    onSecondary = md_theme_dark_onSecondary,
    primaryBackground = md_theme_dark_primaryBackground,
    surfaceBackground = md_theme_dark_surfaceBackground,
    elevationBackground = md_theme_dark_elevationBackground,
    mountainBackground = md_theme_dark_mountainBackground,
    busyStatus = md_theme_dark_busyStatus,
    freeStatus = md_theme_dark_freeStatus,
    onError = md_theme_dark_onError,
    onSuccess = md_theme_dark_onSuccess,
    secondaryButton = md_theme_dark_secondaryButton,
    primaryTextAndIcon = md_theme_dark_primaryTextAndIcon,
    secondaryTextAndIcon = md_theme_dark_secondaryTextAndIcon,
    tertiaryTextAndIcon = md_theme_dark_tertiaryTextAndIcon,
    pressedPrimaryButton = md_theme_dark_pressedPrimaryButton,
    disabledPrimaryButton = md_theme_dark_disabledPrimaryButton
)

private val LightColors = AppThemeColors(
    onPrimary = md_theme_light_onPrimary,
    onSecondary = md_theme_light_onSecondary,
    primaryBackground = md_theme_light_primaryBackground,
    surfaceBackground = md_theme_light_surfaceBackground,
    elevationBackground = md_theme_light_elevationBackground,
    mountainBackground = md_theme_light_mountainBackground,
    busyStatus = md_theme_light_busyStatus,
    freeStatus = md_theme_light_freeStatus,
    onError = md_theme_light_onError,
    onSuccess = md_theme_light_onSuccess,
    secondaryButton = md_theme_light_secondaryButton,
    primaryTextAndIcon = md_theme_light_primaryTextAndIcon,
    secondaryTextAndIcon = md_theme_light_secondaryTextAndIcon,
    tertiaryTextAndIcon = md_theme_light_tertiaryTextAndIcon,
    pressedPrimaryButton = md_theme_light_pressedPrimaryButton,
    disabledPrimaryButton = md_theme_light_disabledPrimaryButton
)
object AppTheme {
    val colors: AppThemeColors
    @Composable
    get() = LocalColors.current

    val typography: AppThemeTypography
    @Composable
    get() = LocalTypography.current
}

val LocalColors = staticCompositionLocalOf<AppThemeColors> {
    error("No colors provided")
}

val LocalTypography = staticCompositionLocalOf<AppThemeTypography> {
    error("No typography provided")
}

@Composable
internal fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = DarkColors
    val typography = AppThemeTypography(
        header1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = 56.sp,
            lineHeight = 56.sp * 1.5f,
            letterSpacing = 56.sp * 0.02f
        ),
        header2 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = 48.sp,
            lineHeight = 48.sp * 1.5f,
            letterSpacing = 48.sp * 0.02f
        ),
        header3 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = 36.sp,
            lineHeight = 36.sp * 1.5f,
            letterSpacing = 36.sp * 0.02f
        ),
        header4 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = 28.sp,
            lineHeight = 28.sp * 1.5f,
            letterSpacing = 28.sp * 0.02f
        ),
        header5 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
            lineHeight = 24.sp * 1.5f,
            letterSpacing = 24.sp * 0.02f
        ),
        header6 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            lineHeight = 20.sp * 1.5f,
            letterSpacing = 20.sp * 0.02f
        ),
        header7 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            lineHeight = 18.sp * 1.5f,
            letterSpacing = 18.sp * 0.02f
        ),
    )


    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides typography
    ) {
        Surface (content = content)
    }

}
