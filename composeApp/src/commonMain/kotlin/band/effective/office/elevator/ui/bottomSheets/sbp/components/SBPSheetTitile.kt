package band.effective.office.elevator.ui.bottomSheets.sbp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.MainRes
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun SBPSheetTitle(
    onClickBack: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onClickBack,
        ) {
            Icon(
                painter = painterResource(MainRes.images.back_button),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(32.dp)
            )
        }
        Text(
            text = "Выбор банка", // TODO
            fontStyle = MaterialTheme.typography.h4.
            copy(
                fontWeight = FontWeight.Black,
                color = Color.Black
            ).fontStyle,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.width(1.dp))
    }
}