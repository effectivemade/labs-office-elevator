package band.effective.office.elevator.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun EffectiveOutlinedButton(icon : ImageResource?, text: StringResource?, onClick:()->Unit){
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(size = 8.dp),
        border = BorderStroke(1.dp, Color(0xFF0C0430E)),
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            icon?.let{
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    tint = Color(0xFF0C0430E)
                )
            }
            text?.let {
                Text(
                    stringResource(text),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}