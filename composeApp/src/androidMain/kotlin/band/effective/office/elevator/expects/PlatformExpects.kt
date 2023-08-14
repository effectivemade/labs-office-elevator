package band.effective.office.elevator.expects

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.AndroidApp
import band.effective.office.elevator.ExtendedTheme
import band.effective.office.elevator.ui.main.components.PopupItem
import band.effective.office.elevator.ui.main.store.MainStore
import band.effective.office.elevator.ui.models.ReservedSeat

actual fun showToast(message: String) {
    Toast.makeText(AndroidApp.INSTANCE.applicationContext, message, Toast.LENGTH_SHORT).show()
}

actual fun generateVibration(milliseconds: Long) {
    val vibrator =
        AndroidApp.INSTANCE.applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= 26) {
        vibrator.vibrate(
            VibrationEffect.createOneShot(
                milliseconds,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    } else {
        vibrator.vibrate(milliseconds)
    }
}

actual fun makeCall(phoneNumber: String) {
    with(AndroidApp.INSTANCE.applicationContext) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        this@with.startActivity(intent)
    }
}

actual fun pickTelegram(telegramNick: String) {
    with(AndroidApp.INSTANCE.applicationContext) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://t.me/$telegramNick")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            `package` = "org.telegram.messenger"
        }
        this@with.startActivity(intent)
    }
}

@SuppressLint("WrongConstant")
actual fun pickSBP(phoneNumber: String) {
    with(AndroidApp.INSTANCE.applicationContext) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("tel:$phoneNumber")
            flags = 0x24000000 or Intent.FLAG_ACTIVITY_NEW_TASK
//            component = ComponentName(
//                "ru.sberbankmobile",
//                "ru.sberbank.mobile.auth.presentation.splash.SplashActivity"
//            )
        }
        this@with.startActivity(intent)
    }
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