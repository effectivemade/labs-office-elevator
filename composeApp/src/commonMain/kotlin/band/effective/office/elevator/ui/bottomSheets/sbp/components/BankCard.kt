package band.effective.office.elevator.ui.bottomSheets.sbp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.generateImageLoader
import band.effective.office.elevator.ui.bottomSheets.sbp.model.SBPBankInfo
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.model.ImageAction
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.rememberImageAction
import com.seiko.imageloader.rememberImageActionPainter
import com.seiko.imageloader.rememberImagePainter
import dev.icerock.moko.resources.compose.painterResource
import io.github.aakira.napier.Napier

@Composable
fun BankCad(
    bankInfo: SBPBankInfo,
    onClick: (SBPBankInfo) -> Unit
) {

    val painter = rememberImagePainter(
        url = bankInfo.logo,
        placeholderPainter = { painterResource(MainRes.images.logo_default) },
        errorPainter = { painterResource(MainRes.images.logo_default) }
    )

    SideEffect {
        Napier.d { "$bankInfo" }
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick(bankInfo) }),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )

    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(48.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text (
                text = bankInfo.bankName,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

//@Composable
//private fun fixedRememberImageAction(
//    url: String,
//    imageLoader: ImageLoader = LocalImageLoader.current,
//): State<ImageAction> {
//    val request = remember(url) { ImageRequest(url) }
//    return rememberImageAction(request, imageLoader)
//}
//
//@Composable
//private fun fixedRememberImagePainter(
//    url: String,
//    imageLoader: ImageLoader = LocalImageLoader.current,
//    filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
//    placeholderPainter: (@Composable () -> Painter)?,
//    errorPainter: (@Composable () -> Painter)?,
//): Painter {
//    val action by fixedRememberImageAction(url, imageLoader)
//    return rememberImageActionPainter(
//        action = action,
//        filterQuality = filterQuality,
//        placeholderPainter = placeholderPainter,
//        errorPainter = errorPainter,
//    )
//}