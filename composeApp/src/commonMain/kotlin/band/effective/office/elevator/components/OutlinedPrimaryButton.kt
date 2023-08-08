package band.effective.office.elevator.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedThemeColors
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
@Composable
fun OutlinedPrimaryButton (onClick: ()-> Unit, title:StringResource){
    OutlinedButton(
        contentPadding = PaddingValues(all = 12.dp),
        onClick = onClick,
        border = BorderStroke(1.dp, ExtendedThemeColors.colors.trinidad_700),
        modifier = Modifier.fillMaxWidth(0.5f),
        shape = RoundedCornerShape(8.dp)
    )
    {
        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight(500),
                color = MaterialTheme.colors.secondary,
                textAlign = TextAlign.Center
            )
        )
    }
}