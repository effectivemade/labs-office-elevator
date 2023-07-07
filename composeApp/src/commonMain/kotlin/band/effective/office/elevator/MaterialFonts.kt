package band.effective.office.elevator

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import dev.icerock.moko.resources.compose.fontFamilyResource

@Composable
fun getFontDefaultFont(): FontFamily {
    return fontFamilyResource(MainRes.fonts.Roboto.regular)
}

@Composable
fun getFontPromoFont(): FontFamily {
    return fontFamilyResource(MainRes.fonts.Museocyrl.regular)
}