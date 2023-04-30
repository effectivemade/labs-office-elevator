package com.example.effecticetv.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import band.effective.office.tv.R

val museoCyrl = FontFamily(Font(R.font.museocyrl))
val drukLCGWideMedium = FontFamily(Font(R.font.druktextwidelcg_medium))
// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = robotoFontFamily(),
//    body1 = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp
//    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

fun robotoFontFamily(): FontFamily =
    FontFamily(
        Font(R.font.roboto_black),
        Font(R.font.roboto_black, weight = FontWeight.Bold),
        Font(R.font.roboto_black, weight = FontWeight.Light),
        Font(R.font.roboto_black, weight = FontWeight.Thin),
        Font(R.font.roboto_black, weight = FontWeight.Normal, style = FontStyle.Italic)
    )