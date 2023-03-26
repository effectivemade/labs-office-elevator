package band.effective.office.tv.screen.LeaderIdEvets

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import band.effective.office.tv.R

fun robotoFontFamily(): FontFamily =
    FontFamily(
        Font(R.font.roboto_black),
        Font(R.font.roboto_black, weight = FontWeight.Bold),
        Font(R.font.roboto_black, weight = FontWeight.Light),
        Font(R.font.roboto_black, weight = FontWeight.Thin),
        Font(R.font.roboto_black, weight = FontWeight.Normal, style = FontStyle.Italic)
    )