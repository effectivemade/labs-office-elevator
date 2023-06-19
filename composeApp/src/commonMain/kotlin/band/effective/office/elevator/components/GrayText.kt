package band.effective.office.elevator.common.compose.components

import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
internal fun GrayText(modifier: Modifier = Modifier, text: String, style: TextStyle) {
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(modifier = modifier, text = text, style = style)
    }
}