package band.effective.office.tablet.ui.theme

import androidx.compose.ui.graphics.Color

internal val md_theme_dark_onPrimary = Color(0xFFEE7234)
internal val md_theme_dark_onSecondary = Color(0xFFA262F7)
internal val md_theme_dark_primaryBackground = Color(0xFF1E1C1A)
internal val md_theme_dark_surfaceBackground = Color(0xFF252322)
internal val md_theme_dark_elevationBackground = Color(0xFF302D2C)
internal val md_theme_dark_mountainBackground = Color(0xFF3A3736)
internal val md_theme_dark_busyStatus = Color(0xFFF84C4C)
internal val md_theme_dark_freeStatus = Color(0xFF36C85F)
internal val md_theme_dark_onError = Color(0xFFEA4C2A)
internal val md_theme_dark_onSuccess = Color(0xFF25C050)
internal val md_theme_dark_secondaryButton = Color(0xFFFEFEFE)
internal val md_theme_dark_primaryTextAndIcon = Color(0xFFF9F9F9)
internal val md_theme_dark_secondaryTextAndIcon = Color(0xFF7F7F7F)
internal val md_theme_dark_tertiaryTextAndIcon = Color(0xFF777777)
internal val md_theme_dark_pressedPrimaryButton = Color(0xFFEC6521)
internal val md_theme_dark_disabledPrimaryButton = Color(0xFF342C28)


internal val md_theme_light_onPrimary = Color(0xFFE55A0F)
internal val md_theme_light_onSecondary = Color(0xFF6E00FE)
internal val md_theme_light_primaryBackground = Color(0xFFFEFEFE)
internal val md_theme_light_surfaceBackground = Color(0xFFF9F9F9)
internal val md_theme_light_elevationBackground = Color(0xFFEFEFEF)
internal val md_theme_light_mountainBackground = Color(0xFFEFEFEF)
internal val md_theme_light_busyStatus = Color(0xFFF84343)
internal val md_theme_light_freeStatus = Color(0xFF36C85F)
internal val md_theme_light_onError = Color(0xFFEA4C2A)
internal val md_theme_light_onSuccess = Color(0xFF25C050)
internal val md_theme_light_secondaryButton = Color(0xFFE55A0F)
internal val md_theme_light_primaryTextAndIcon = Color(0xFF1F1814)
internal val md_theme_light_secondaryTextAndIcon = Color(0xFF747474)
internal val md_theme_light_tertiaryTextAndIcon = Color(0xFF979797)
internal val md_theme_light_pressedPrimaryButton = Color(0xFFED6F2F)
internal val md_theme_light_disabledPrimaryButton = Color(0xFFD1C9C5)


data class AppThemeColors(
    val onPrimary: Color,
    val onSecondary: Color,

    val primaryBackground: Color,
    val surfaceBackground: Color,
    val elevationBackground: Color,
    val mountainBackground: Color,

    val busyStatus: Color,
    val freeStatus: Color,

    val onError: Color,
    val onSuccess: Color,

    val secondaryButton: Color,
    val primaryTextAndIcon: Color,
    val secondaryTextAndIcon: Color,
    val tertiaryTextAndIcon: Color,

    val pressedPrimaryButton: Color,
    val disabledPrimaryButton: Color
)