package band.effective.office.elevator

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp


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
    val typography = Typography(
        defaultFontFamily = getDefaultFont(),

//          region:: H
        h4 = TextStyle(
            fontWeight = FontWeight(400),
            fontSize = 40.sp,
            fontFamily = getPromoFont(),
            letterSpacing = 0.1.sp,
            lineHeight = 40.sp,
            textAlign = TextAlign.Start
        ),

        h5 = TextStyle(
            fontWeight = FontWeight(600),
            fontSize = 28.sp,
            letterSpacing = 0.1.sp,
            textAlign = TextAlign.Start,
            color = Color(0xFF5800CB),
            lineHeight = 36.4.sp
        ),

        h6 = TextStyle(
            fontSize = 20.sp,
            lineHeight = 26.sp,
            fontWeight = FontWeight(600),
            textAlign = TextAlign.Center,
            letterSpacing = 0.1.sp,
        ),

//          endregion

//          region::Subtitles
        subtitle1 = TextStyle(
            fontSize = 15.sp,
            lineHeight = 19.5.sp,
            letterSpacing = 0.1.sp,
//          Setup font weight and color
        ),
//          endregion

//          region::Body styles
        body1 = TextStyle(
            fontWeight = FontWeight(500),
            fontSize = 16.sp,
            lineHeight = 20.8.sp,
            letterSpacing = 0.1.sp,
            textAlign = TextAlign.Start,
            color = theme_light_tertiary_color
        ),

        body2 = TextStyle(
            fontSize = 14.sp,
            lineHeight = 18.2.sp,
            fontWeight = FontWeight(500),
            color = theme_light_secondary_color,
            letterSpacing = 0.1.sp,
        ),

        caption = TextStyle(
            fontSize = 12.sp,
            lineHeight = 15.6.sp,
            fontWeight = FontWeight(500),
            color = theme_light_tertiary_color,
            textAlign = TextAlign.Center,
            letterSpacing = 0.1.sp,
        ),
//          endregion

//          region::Button styles
        button = TextStyle(
            fontWeight = FontWeight(500),
            fontSize = 16.sp,
            letterSpacing = 0.1.sp,
            lineHeight = 20.8.sp,
            textAlign = TextAlign.Center
        ),
//          endregion

    )
    val colors = if (!useDarkTheme) {
        LightColors
    } else {
        DarkColors
    }

    ExtendedTheme(
        content = {
            Surface(content = content)
        }
    )
}

@Composable
fun ExtendedTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val typography = Typography(
        defaultFontFamily = getDefaultFont(),

//          region:: H
        h4 = TextStyle(
            fontWeight = FontWeight(400),
            fontSize = 40.sp,
            fontFamily = getPromoFont(),
            letterSpacing = 0.1.sp,
            lineHeight = 40.sp,
            textAlign = TextAlign.Start
        ),

        h5 = TextStyle(
            fontWeight = FontWeight(600),
            fontSize = 28.sp,
            letterSpacing = 0.1.sp,
            textAlign = TextAlign.Start,
            color = Color(0xFF5800CB),
            lineHeight = 36.4.sp
        ),

        h6 = TextStyle(
            fontSize = 20.sp,
            lineHeight = 26.sp,
            fontWeight = FontWeight(600),
            textAlign = TextAlign.Center,
            letterSpacing = 0.1.sp,
        ),

//          endregion

//          region::Subtitles
        subtitle1 = TextStyle(
            fontSize = 15.sp,
            lineHeight = 19.5.sp,
            letterSpacing = 0.1.sp,
//          Setup font weight and color
        ),
//          endregion

//          region::Body styles
        body1 = TextStyle(
            fontWeight = FontWeight(500),
            fontSize = 16.sp,
            lineHeight = 20.8.sp,
            letterSpacing = 0.1.sp,
            textAlign = TextAlign.Start,
            color = theme_light_tertiary_color
        ),

        body2 = TextStyle(
            fontSize = 14.sp,
            lineHeight = 18.2.sp,
            fontWeight = FontWeight(500),
            color = theme_light_secondary_color,
            letterSpacing = 0.1.sp,
        ),

        caption = TextStyle(
            fontSize = 12.sp,
            lineHeight = 15.6.sp,
            fontWeight = FontWeight(500),
            color = theme_light_tertiary_color,
            textAlign = TextAlign.Center,
            letterSpacing = 0.1.sp,
        ),
//          endregion

//          region::Button styles
        button = TextStyle(
            fontWeight = FontWeight(500),
            fontSize = 16.sp,
            letterSpacing = 0.1.sp,
            lineHeight = 20.8.sp,
            textAlign = TextAlign.Center
        ),
//          endregion

    )
    val colors = if (!useDarkTheme) {
        LightColors
    } else {
        DarkColors
    }
//    region::Extended colors
    val extendedColors = ExtendedColors(
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
//    endregion

    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            typography = typography,
            colors = colors,
            content = {
                Surface(content = content)
            }
        )
    }
}

object ExtendedTheme {
    val colors: ExtendedColors
        @Composable
        get() = LocalExtendedColors.current
}
