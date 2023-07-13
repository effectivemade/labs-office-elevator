package band.effective.office.elevator

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

//generated by https://m3.material.io/theme-builder#/custom
//Color palette was taken here: https://colorhunt.co/palettes/popular

//region::Old light colors
internal val md_theme_light_primary = Color(0xFFFFFFFF)
internal val md_theme_light_onPrimary = Color(0xFFFFFFFF)
internal val md_theme_light_primaryContainer = Color(0xFFABEDFF)
internal val md_theme_light_onPrimaryContainer = Color(0xFF001F26)
internal val md_theme_light_secondary = Color(0xFF9563FF)
internal val md_theme_light_onSecondary = Color(0xFFFFFFFF)
internal val md_theme_light_secondaryContainer = Color(0xFF6FF6FE)
internal val md_theme_light_onSecondaryContainer = Color(0xFF002022)
internal val md_theme_light_tertiary = Color(0xFF904D00)
internal val md_theme_light_onTertiary = Color(0xFFFFFFFF)
internal val md_theme_light_tertiaryContainer = Color(0xFFFFDCC2)
internal val md_theme_light_onTertiaryContainer = Color(0xFF2E1500)
internal val md_theme_light_error = Color(0xFFBA1A1A)
internal val md_theme_light_errorContainer = Color(0xFFFFDAD6)
internal val md_theme_light_onError = Color(0xFFFFFFFF)
internal val md_theme_light_onErrorContainer = Color(0xFF410002)
internal val md_theme_light_background = Color(0xFFF7F7F7)
internal val md_theme_light_onBackground = Color(0xFF221B00)
internal val md_theme_light_surface = Color(0xFFFFFBFF)
internal val md_theme_light_onSurface = Color(0xFF221B00)
internal val md_theme_light_surfaceVariant = Color(0xFFDBE4E7)
internal val md_theme_light_onSurfaceVariant = Color(0xFF3F484B)
internal val md_theme_light_outline = Color(0xFF70797B)
internal val md_theme_light_inverseOnSurface = Color(0xFFFFF0C0)
internal val md_theme_light_inverseSurface = Color(0xFF3A3000)
internal val md_theme_light_inversePrimary = Color(0xFF55D6F4)
internal val md_theme_light_shadow = Color(0xFF000000)
internal val md_theme_light_surfaceTint = Color(0xFF00687A)
internal val md_theme_light_outlineVariant = Color(0xFFBFC8CB)
internal val md_theme_light_scrim = Color(0xFF000000)
//endregion

//region::Old dark colors
internal val md_theme_dark_primary = Color(0xFF626161)
internal val md_theme_dark_onPrimary = Color(0xFF003640)
internal val md_theme_dark_primaryContainer = Color(0xFF004E5C)
internal val md_theme_dark_onPrimaryContainer = Color(0xFFABEDFF)
internal val md_theme_dark_secondary = Color(0xFF9563FF)
internal val md_theme_dark_onSecondary = Color(0xFF00373A)
internal val md_theme_dark_secondaryContainer = Color(0xFF004F53)
internal val md_theme_dark_onSecondaryContainer = Color(0xFF6FF6FE)
internal val md_theme_dark_tertiary = Color(0xFFFFB77C)
internal val md_theme_dark_onTertiary = Color(0xFF4D2700)
internal val md_theme_dark_tertiaryContainer = Color(0xFF6D3900)
internal val md_theme_dark_onTertiaryContainer = Color(0xFFFFDCC2)
internal val md_theme_dark_error = Color(0xFFFFB4AB)
internal val md_theme_dark_errorContainer = Color(0xFF93000A)
internal val md_theme_dark_onError = Color(0xFF690005)
internal val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
internal val md_theme_dark_background = Color(0xFF434040)
internal val md_theme_dark_onBackground = Color(0xFFFFE264)
internal val md_theme_dark_surface = Color(0xFF221B00)
internal val md_theme_dark_onSurface = Color(0xFFFFFFFF)
internal val md_theme_dark_surfaceVariant = Color(0xFF3F484B)
internal val md_theme_dark_onSurfaceVariant = Color(0xFFBFC8CB)
internal val md_theme_dark_outline = Color(0xFF899295)
internal val md_theme_dark_inverseOnSurface = Color(0xFF221B00)
internal val md_theme_dark_inverseSurface = Color(0xFFFFE264)
internal val md_theme_dark_inversePrimary = Color(0xFF00687A)
internal val md_theme_dark_shadow = Color(0xFF000000)
internal val md_theme_dark_surfaceTint = Color(0xFF55D6F4)
internal val md_theme_dark_outlineVariant = Color(0xFF3F484B)
internal val md_theme_dark_scrim = Color(0xFF000000)
//endregion


//region::New light colors
internal val theme_light_primary = Color(0xFFE85B0F)
internal val theme_light_onPrimary = Color(0xFFFFFFFF)

internal val theme_light_secondary = Color(0xFFC2410C)
internal val theme_light_onSecondary = Color(0xFFFFFFFF)

internal val theme_light_secondary_variant = Color(0xFF6F01FF)

internal val theme_light_tertiary = Color(0xFFDB6242)
internal val theme_light_onTertiary = Color(0xFFFFFFFF)

internal val theme_light_error = Color(0xFFBA1A1A)
internal val theme_light_onError = Color(0xFFFFFFFF)

internal val theme_light_background = Color(0xFFF9F2F3)
internal val theme_light_onBackground = Color(0xFFECECEC) //Background: secondary

internal val theme_light_surface = Color(0xFFFFFBFF)
internal val theme_light_onSurface = Color(0xFF221B00)

internal val theme_light_primary_color = Color(0xFFFFFFFF)
internal val theme_light_secondary_color = Color(0xFFC2410C)
internal val theme_light_tertiary_color = Color(0xFF000000)

internal val theme_light_primary_stroke = Color(0xFFF8933F)
internal val theme_light_secondary_stroke = Color(0x00000066)

internal val theme_light_primary_icon_color = Color(0xFF0C0430E)
internal val theme_light_secondary_icon_color = Color(0xFF6F01FF)
internal val theme_light_tertiary_icon_color = Color(0x00000066)
//endregion

//TODO :
// Заполнить тёмными цветами,
// в теме эти цвета используются
//region::New dark colors
internal val theme_dark_primary = Color(0xFF626161)
internal val theme_dark_onPrimary = Color(0xFF003640)

internal val theme_dark_secondary = Color(0xFF9563FF)
internal val theme_dark_onSecondary = Color(0xFF00373A)

internal val theme_dark_tertiary = Color(0xFFFFB77C)
internal val theme_dark_onTertiary = Color(0xFF4D2700)

internal val theme_dark_error = Color(0xFFFFB4AB)
internal val theme_dark_onError = Color(0xFF690005)

internal val theme_dark_background = Color(0xFF434040)
internal val theme_dark_onBackground = Color(0xFFFFE264)
internal val theme_dark_surface = Color(0xFF221B00)
internal val theme_dark_onSurface = Color(0xFFFFFFFF)

internal val theme_dark_primary_color = Color(0xFFFFFFFF)
internal val theme_dark_secondary_color = Color(0xFFC2410C)
internal val theme_dark_tertiary_color = Color(0x00000080)

internal val theme_dark_primary_stroke = Color(0xFFF8933F)
internal val theme_dark_secondary_stroke = Color(0x00000066)

internal val theme_dark_primary_icon_color = Color(0xFF0C0430E)
internal val theme_dark_secondary_icon_color = Color(0xFF6F01FF)
internal val theme_dark_tertiary_icon_color = Color(0x00000066)
//endregion

//region::Extended colors
@Immutable
data class ExtendedColors(
//    region::Trinidad
    val trinidad_50: Color,
    val trinidad_100: Color,
    val trinidad_200: Color,
    val trinidad_300: Color,
    val trinidad_400: Color,
    val trinidad_500: Color,
    val trinidad_600: Color,
    val trinidad_700: Color,
    val trinidad_800: Color,
    val trinidad_900: Color,
    val trinidad_950: Color,
//    endregion

//    region::Purple heart
    val purple_heart_50: Color,
    val purple_heart_100: Color,
    val purple_heart_200: Color,
    val purple_heart_300: Color,
    val purple_heart_400: Color,
    val purple_heart_500: Color,
    val purple_heart_600: Color,
    val purple_heart_700: Color,
    val purple_heart_800: Color,
    val purple_heart_900: Color,
    val purple_heart_950: Color,
//    endregion

    val error: Color
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        trinidad_50 = Color(0xFFFFF7ED),
        trinidad_100 = Color(0xFFFEEDD6),
        trinidad_200 = Color(0xFFFDD8AB),
        trinidad_300 = Color(0xFFFBBB76),
        trinidad_400 = Color(0xFFF8933F),
        trinidad_500 = Color(0xFFF67619),
        trinidad_600 = Color(0xFFE85B0F),
        trinidad_700 = Color(0xFFC0430E),
        trinidad_800 = Color(0xFF983614),
        trinidad_900 = Color(0xFF7B2F13),
        trinidad_950 = Color(0xFF421408),

        purple_heart_50 = Color(0xFFF4F0FF),
        purple_heart_100 = Color(0xFFEBE4FF),
        purple_heart_200 = Color(0xFFD9CDFF),
        purple_heart_300 = Color(0xFFBFA5FF),
        purple_heart_400 = Color(0xFFA172FF),
        purple_heart_500 = Color(0xFF873AFF),
        purple_heart_600 = Color(0xFF7C12FF),
        purple_heart_700 = Color(0xFF6F01FF),
        purple_heart_800 = Color(0xFF5800CB),
        purple_heart_900 = Color(0xFF4E02B0),
        purple_heart_950 = Color(0xFF2D0078),
        error = Color(0xFFFF3B30)
    )
}
//endregion

internal val seed = Color(0xFF2C3639)

// Non Material specified colors
internal val lightGray = Color(0xFFEBEBEB)
internal val textInBorderGray = Color (0xFF808080)
internal val borderGray = Color (0xFF666666)
internal val successGreen = Color(0xFF4BB543)

internal val borderGreen = Color(0xFF34C759)
internal val textInBorderPurple = Color(0xFF5800CB)
internal val borderPurple = Color(0xFF6F01FF)
internal val textGrayColor = Color(0x80000000)

