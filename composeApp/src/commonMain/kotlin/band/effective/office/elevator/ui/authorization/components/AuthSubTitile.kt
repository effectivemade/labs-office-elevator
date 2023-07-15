package band.effective.office.elevator.ui.authorization.components

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import band.effective.office.elevator.theme_light_tertiary_color

@Composable
fun AuthSubTitle(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign?,
) {
    Text(
        text = text,
        modifier = Modifier
            .wrapContentSize()
            .then(modifier),
        style = MaterialTheme.typography.body1,
        textAlign = textAlign,
        color = theme_light_tertiary_color,
    )
}