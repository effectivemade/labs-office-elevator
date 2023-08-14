package band.effective.office.elevator.expects

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedTheme
import band.effective.office.elevator.ui.main.store.MainStore
import band.effective.office.elevator.ui.models.ReservedSeat
import band.effective.office.elevator.ui.uiViewController
import io.github.aakira.napier.Napier
import platform.UIKit.UIAlertController
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.Foundation.*
import platform.UIKit.*

actual fun showToast(message: String) {
    Napier.e { message }
    val controller = UIAlertController(null, null)
    controller.message = message
    controller.showViewController(uiViewController, null)
}

actual fun generateVibration(milliseconds: Long) {
//    AudioServicesPlaySystemSound(kSystemSoundID_Vibrate)
}

actual fun makeCall(phoneNumber: String) {
    val url = NSURL(string = "tel:$phoneNumber")
    UIApplication.sharedApplication.openURL(url)
}

actual fun pickTelegram(telegramNick: String) {
    val urlString = "https://telegram.me/$telegramNick"
    val url = NSURL.URLWithString(urlString)
    val application = UIApplication.sharedApplication
    if (url != null) {
        application.openURL(url)
    }
}

actual fun pickSBP(phoneNumber: String) {
    val url = NSURL(string = "tel:$phoneNumber")
    UIApplication.sharedApplication.openURL(url)
}

/*
@Composable
actual fun showPopupMenu(expand: MutableState<Boolean>, onItemSelected: (Int) -> Unit) {

    val dropDownList =
        listOf(
            "Показать карту",
            "Продлить",
            "Повторить",
            "Удалить"
        )
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = 0.dp,
            alignment = Alignment.CenterVertically
        ),
        modifier = Modifier
            .padding(end = 24.dp, bottom = 24.dp)
            .wrapContentSize()
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.White)
    ) {
        DropdownMenu(
            expanded = expand.value,
            onDismissRequest = {
                expand.value = false
            }
        ) {
            dropDownList.forEachIndexed { index, stringResource ->
                Column {
                    DropdownMenuItem(
                        onClick = {
                            expand.value = !expand.value
                            onItemSelected(index)
                        }
                    ) {
                        Text(
                            text = stringResource,
                            style = MaterialTheme.typography.button.copy(color = MaterialTheme.colors.secondary)
                        )
                    }
                }
            }
        }
    }
}
 */
