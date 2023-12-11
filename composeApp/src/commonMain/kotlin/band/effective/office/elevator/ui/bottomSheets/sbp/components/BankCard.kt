package band.effective.office.elevator.ui.bottomSheets.sbp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.generateImageLoader
import band.effective.office.elevator.ui.bottomSheets.sbp.model.SBPBankInfo
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.rememberImagePainter
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun BankCad(
    bankInfo: SBPBankInfo,
    onClick: (SBPBankInfo) -> Unit
) {
    val imageLoader = generateImageLoader()

    val request = remember(bankInfo.logo) {
        ImageRequest {
            data(bankInfo.logo)
        }
    }

    val painter = rememberImagePainter(
        request = request,
        imageLoader = imageLoader,
        placeholderPainter = { painterResource(MainRes.images.logo_default) },
        errorPainter = { painterResource(MainRes.images.logo_default) }
    )

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.clickable(onClick = { onClick(bankInfo) })
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painter,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text (
                text = bankInfo.bankName,
                style = MaterialTheme.typography.body1
            )
        }
    }
}